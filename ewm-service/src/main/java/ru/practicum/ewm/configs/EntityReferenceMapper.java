package ru.practicum.ewm.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.event.EventRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.exceptions.NotFoundException;

import static ru.practicum.ewm.configs.Constants.EVENT;


@Component
@RequiredArgsConstructor
public class EntityReferenceMapper {

    private final EventRepository eventRepository;

    public Event getById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException(EVENT, eventId));
    }
}