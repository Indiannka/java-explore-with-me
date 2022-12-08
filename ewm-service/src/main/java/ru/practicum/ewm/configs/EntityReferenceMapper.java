package ru.practicum.ewm.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.comment.CommentRepository;
import ru.practicum.ewm.comment.converter.CommentMapper;
import ru.practicum.ewm.comment.model.dto.CommentDto;
import ru.practicum.ewm.event.EventRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.exceptions.NotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;

import static ru.practicum.ewm.configs.Constants.EVENT;


@Component
@RequiredArgsConstructor
public class EntityReferenceMapper {

    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public Event getById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException(EVENT, eventId));
    }

    public Collection<CommentDto> getAll(Long eventId) {
        return commentRepository.findAllByEventIdAndParentIdIsNull(eventId).stream()
                .map(commentMapper::convertToDto)
                .collect(Collectors.toList());
    }
}