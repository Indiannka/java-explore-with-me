package ru.practicum.ewm.event;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.event.converter.EventMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.QEvent;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.event.model.dto.AdminUpdateEventDto;
import ru.practicum.ewm.event.model.dto.NewEventDto;
import ru.practicum.ewm.event.model.dto.SearchParameters;
import ru.practicum.ewm.event.model.dto.UpdateEventRequest;
import ru.practicum.ewm.exceptions.EventDateException;
import ru.practicum.ewm.exceptions.EventStateException;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.exceptions.UserAccessRightsException;
import ru.practicum.ewm.model.EndpointHit;
import ru.practicum.ewm.user.UserRepository;
import ru.practicum.ewm.user.model.User;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import static ru.practicum.ewm.configs.Constants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private static final boolean UNIQUE_VIEWS = false;

    private final EventMapper eventMapper;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final StatsClient client;

    @Override
    @Transactional
    public Collection<Event> search(SearchParameters params) {
        QEvent event = QEvent.event;
        BooleanBuilder predicate = new BooleanBuilder(event.state.eq(State.PUBLISHED));
        if (params.getText() != null) {
            predicate.and(event.annotation.containsIgnoreCase(params.getText()))
                    .or(event.description.containsIgnoreCase(params.getText()));
        }
        if (params.getCategories() != null && !params.getCategories().isEmpty()) {
            predicate.and(event.category.id.in(params.getCategories()));
        }
        if (params.getPaid() != null) {
            predicate.and(event.paid.eq(params.getPaid()));
        }
        predicate.and(event.eventDate.goe(params.getRangeStart() != null ? params.getRangeStart() : LocalDateTime.now()));

        if (params.getRangeStart() != null) {
            predicate.and(event.eventDate.loe(params.getRangeEnd()));
        }
        if (params.isOnlyAvailable()) {
            predicate.and(event.confirmedRequests.gt(0)).and(event.confirmedRequests.loe(event.participantLimit));
        }
        Sort sort = Sort.by(Sort.Direction.DESC, params.getSort().getValue());
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), sort);

        client.sendHit(createHit(params.getRequest()));

        return eventRepository.findAll(predicate, pageable).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Event> getAllAdmin(SearchParameters params) {
        QEvent event = QEvent.event;
        BooleanBuilder predicate = new BooleanBuilder();
        if (params.getUsers() != null && !params.getUsers().isEmpty()) {
            predicate.and(event.initiator.id.in(params.getUsers()));
        }
        if (params.getStates() != null && !params.getStates().isEmpty()) {
            predicate.and(event.state.in(params.getStates().stream().map(s -> State.valueOf(s.toUpperCase())).collect(Collectors.toList())));
        }
        if (params.getCategories() != null && !params.getCategories().isEmpty()) {
            predicate.and(event.category.id.in(params.getCategories()));
        }
        predicate.and(event.eventDate.goe(params.getRangeStart() != null ? params.getRangeStart() : LocalDateTime.now()));

        if (params.getRangeStart() != null) {
            predicate.and(event.eventDate.loe(params.getRangeEnd()));
        }
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize());
        return eventRepository.findAll(predicate, pageable).toList();
    }

    @Override
    @Transactional
    public Event getById(Long eventId, HttpServletRequest request) {

        Event event = eventRepository.findByIdAndState(eventId, State.PUBLISHED).orElseThrow(
                () -> new NotFoundException(EVENT, eventId));

        ResponseEntity<Object> response = null;

        try {
            response = client.sendHit(createHit(request));
        } catch (Exception exception) {
            log.error("An error {} occurred while sending statistics to stats server", exception.getMessage());
        }

        if (response != null && response.getStatusCode().is2xxSuccessful()) {
            int views = 0;
            try {
                views = getViews(event, new String[]{request.getRequestURI()});
                event.setViews(views);
                eventRepository.save(event);
            } catch (Exception exception) {
                log.error("An error {} occurred while retrieving statistics from stats server", exception.getMessage());
            }
        }
        return event;
    }

    private int getViews(Event event, String [] uri) {
        ResponseEntity<Object> response = client.getStats(event.getPublishedOn(), LocalDateTime.now(), uri,UNIQUE_VIEWS);
        ArrayList<LinkedHashMap<String, Integer>> stats = (ArrayList<LinkedHashMap<String, Integer>>)response.getBody();
        if (stats != null) {
            return stats.get(0).get("hits");
        }
        return 0;
    }

    @Override
    @Transactional
    public Event create(NewEventDto newEventDto, Long userId) {
        Event event = eventMapper.convert(newEventDto);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(USER, userId));
        event.setInitiator(user);
        event.setCreatedOn(LocalDateTime.now());
        return eventRepository.save(event);
    }

    @Override
    @Transactional(readOnly = true)
    public Event getByOwner(Long userId, Long eventId) {
        return eventRepository.findByIdAndInitiatorId(eventId, userId).orElseThrow(
                () -> new NotFoundException(EVENT, eventId));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Event> getAllByOwner(Long userId, Pageable pageable) {
        return eventRepository.findAllByInitiatorId(userId, pageable).toList();
    }

    @Override
    @Transactional
    public Event update(long userId, UpdateEventRequest eventRequest) {
        Event event = eventRepository.findById(eventRequest.getEventId()).orElseThrow(
                () -> new NotFoundException(EVENT,eventRequest.getEventId()));
        checkEventBeforeUpdate(event, userId);
        event.setState(State.PENDING);
        eventMapper.updateFromEventDto(eventRequest, event);
        return eventRepository.save(event);
    }

    @Override
    @Transactional
    public Event updateAdmin(AdminUpdateEventDto eventDto, Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException(EVENT,eventId));
        return eventRepository.save(eventMapper.updateFromAdminDto(eventDto, event));
    }

    @Override
    @Transactional
    public Event publish(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException(EVENT,eventId));
        checkEventBeforePublish(event);
        event.setPublishedOn(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        event.setState(State.PUBLISHED);
        return eventRepository.save(event);
    }

    @Override
    @Transactional
    public Event reject(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException(EVENT,eventId));
        if (!event.getState().equals(State.PENDING)) {
            throw new EventStateException(event.getState().toString());
        }
        event.setState(State.CANCELED);
        return eventRepository.save(event);
    }

    @Override
    @Transactional
    public Event cancel(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException(EVENT,eventId));
        checkEventBeforeCancel(event, userId);
        event.setState(State.CANCELED);
        return eventRepository.save(event);
    }

    private EndpointHit createHit(HttpServletRequest request) {
        return EndpointHit.builder()
                .app(APP_CODE)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();
    }

    private void checkEventBeforeUpdate(Event event, Long userId) {
        if (!event.getInitiator().getId().equals(userId)) {
            throw new UserAccessRightsException();
        }
        if (event.getState().equals(State.PUBLISHED)) {
            throw new EventStateException(event.getState().toString());
        }
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new EventDateException(2, "changing");
        }
    }

    private void checkEventBeforeCancel(Event event, Long userId) {
        if (!event.getInitiator().getId().equals(userId)) {
            throw new UserAccessRightsException();
        }
        if (!event.getState().equals(State.PENDING)) {
            throw new EventStateException(event.getState().toString());
        }
    }

    private void checkEventBeforePublish(Event event) {
        if (!event.getState().equals(State.PENDING)) {
            throw new EventStateException(event.getState().toString());
        }
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(1).plusSeconds(30))) {
            throw new EventDateException(1, "publishing");
        }
    }
}