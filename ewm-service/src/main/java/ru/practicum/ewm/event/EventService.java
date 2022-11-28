package ru.practicum.ewm.event;

import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.dto.AdminUpdateEventDto;
import ru.practicum.ewm.event.model.dto.NewEventDto;
import ru.practicum.ewm.event.model.dto.SearchParameters;
import ru.practicum.ewm.event.model.dto.UpdateEventRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public interface EventService {

    Collection<Event> search(SearchParameters searchParameters);

    Collection<Event> getAllAdmin(SearchParameters searchParameters);

    Collection<Event> getAllByOwner(Long userId, int from, int size);

    Event getById(Long eventId, HttpServletRequest request);

    Event getByOwner(Long userId, Long eventId);

    Event create(NewEventDto newEventDto, Long userId);

    Event update(long userId, UpdateEventRequest eventRequest);

    Event updateAdmin(AdminUpdateEventDto eventDto, Long eventId);

    Event cancel(Long userId, Long eventId);

    Event publish(Long eventId);

    Event reject(Long eventId);

}