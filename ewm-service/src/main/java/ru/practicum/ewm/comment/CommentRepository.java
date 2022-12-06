package ru.practicum.ewm.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.ewm.comment.model.Comment;

import java.util.Collection;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, QuerydslPredicateExecutor<Comment> {

    Optional<Comment> findByIdAndAuthorId(Long commentId, Long userId);

    Collection<Comment> findAllByEventId(Long eventId);

    Collection<Comment> findAllByEventIdAndParentIdIsNull(Long eventId);

    Collection<Comment> findAllByParentId(Long commentId);
}