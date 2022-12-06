package ru.practicum.ewm.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.comment.converter.CommentMapper;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.comment.model.dto.NewCommentDto;
import ru.practicum.ewm.event.EventRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.exceptions.CommentAccessException;
import ru.practicum.ewm.exceptions.CommentUpdateException;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.user.UserRepository;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import static ru.practicum.ewm.configs.Constants.*;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final int daysLimit = 1;

    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public Collection<Comment> getAll(Long eventId) {
        return commentRepository.findAllByEventId(eventId);
    }

    @Override
    public Collection<Comment> getAllReplies(Long commentId) {
        return commentRepository.findAllByParentId(commentId);
    }

    @Override
    public Comment getById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException(COMMENT, commentId));
    }

    @Override
    public Comment create(NewCommentDto newCommentDto, Long userId, Long eventId) {
        Event event = eventRepository.findByIdAndState(eventId, State.PUBLISHED).orElseThrow(() -> new NotFoundException(EVENT, eventId));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER, userId));
        Comment comment = commentMapper.convert(newCommentDto);
        return commentRepository.save(createComment(user, event, comment));
    }

    @Override
    public Comment reply(NewCommentDto newCommentDto, Long commentId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER, userId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException(COMMENT, commentId));
        Comment replyComment = createComment(user, comment.getEvent(), commentMapper.convert(newCommentDto));
        replyComment.setParentId(commentId);
        comment.setHasReplies(true);
        commentRepository.save(comment);
        return commentRepository.save(replyComment);
    }

    @Override
    public Comment update(NewCommentDto newCommentDto, Long commentId, Long userId) {
        Comment comment = commentRepository.findByIdAndAuthorId(commentId, userId).orElseThrow(CommentAccessException::new);
        if (comment.getCreated().isBefore(LocalDateTime.now().minusDays(daysLimit))) {
            throw new CommentUpdateException();
        }
        return commentRepository.save(commentMapper.updateFromDto(newCommentDto, comment));
    }

    @Override
    public void delete(Long commentId, Long userId) {
        if (!commentRepository.findByIdAndAuthorId(commentId, userId).isPresent()) {
            throw new CommentAccessException();
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    public void deleteByAdmin(Long[] ids) {
        if (ids.length != 0) {
            commentRepository.deleteAllByIdInBatch(Arrays.asList(ids));
        }
    }

    private Comment createComment(User user, Event event, Comment comment) {
        comment.setCreated(LocalDateTime.now());
        comment.setAuthor(user);
        comment.setEvent(event);
        return comment;
    }
}