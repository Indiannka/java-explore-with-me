package ru.practicum.ewm.comment;

import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.comment.model.dto.NewCommentDto;

import java.util.Collection;

public interface CommentService {

    Collection<Comment> getAll(Long eventId);

    Collection<Comment> getAllReplies(Long commentId);

    Comment getById(Long commentId);

    Comment create(NewCommentDto newCommentDto, Long userId, Long eventId);

    Comment reply(NewCommentDto newCommentDto, Long commentId, Long userId);

    Comment update(NewCommentDto newCommentDto, Long commentId, Long userId);

    void delete(Long commentId, Long userId);

    void deleteByAdmin(Long[] ids);

}