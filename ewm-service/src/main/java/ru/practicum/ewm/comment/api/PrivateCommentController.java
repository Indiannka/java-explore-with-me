package ru.practicum.ewm.comment.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.CommentService;
import ru.practicum.ewm.comment.converter.CommentMapper;
import ru.practicum.ewm.comment.model.dto.CommentDto;
import ru.practicum.ewm.comment.model.dto.NewCommentDto;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class PrivateCommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping("/{userId}/events/{eventId}/comments")
    public CommentDto create(@Valid @RequestBody NewCommentDto newCommentDTO,
                             @PathVariable Long userId,
                             @PathVariable Long eventId) {
        log.info("POST request: добавлен новый комментарий с текстом: {}", newCommentDTO.getText());
        return commentMapper.convertToDto(commentService.create(newCommentDTO, userId, eventId));
    }

    @PatchMapping("/{userId}/comments/{commentId}")
    public CommentDto update(@PathVariable Long userId,
                             @PathVariable Long commentId,
                             @RequestBody NewCommentDto newCommentDTO) {
        log.info("PATCH request: изменен комментарий id={}, новый текст: {}",commentId, newCommentDTO.getText());
        return commentMapper.convertToDto(commentService.update(newCommentDTO, commentId, userId));
    }

    @PostMapping("/{userId}/comments/{commentId}")
    public CommentDto reply(@PathVariable Long userId,
                            @PathVariable Long commentId,
                            @RequestBody NewCommentDto newCommentDTO) {
        log.info("POST request: добавлен комментарий {} к существующему комментарию id={}", newCommentDTO.getText(), commentId);
        return commentMapper.convertToDto(commentService.reply(newCommentDTO, commentId, userId));
    }

    @DeleteMapping("/{userId}/comments/{commentId}")
    public void delete(@PathVariable Long userId,
                       @PathVariable Long commentId) {
        log.info("DELETE request: удален комментарий id={}",commentId);
        commentService.delete(commentId, userId);
    }
}