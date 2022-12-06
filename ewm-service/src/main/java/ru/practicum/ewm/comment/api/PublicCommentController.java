package ru.practicum.ewm.comment.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.comment.CommentService;
import ru.practicum.ewm.comment.converter.CommentMapper;
import ru.practicum.ewm.comment.model.dto.CommentDto;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PublicCommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping("/comments/{commentId}")
    public Collection<CommentDto> getAllReplies(@PathVariable Long commentId) {
        log.info("GET request: запрос ответов к комментарию id={}", commentId);
        return commentService.getAllReplies(commentId).stream()
                .map(commentMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("events/{eventId}/comments")
    public Collection<CommentDto> getAll(@PathVariable Long eventId) {
        log.info("GET request: запрос всех комментариев");
        return commentService.getAll(eventId).stream()
                .map(commentMapper::convertToDto)
                .collect(Collectors.toList());
    }
}