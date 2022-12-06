package ru.practicum.ewm.comment.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.comment.model.dto.CommentDto;
import ru.practicum.ewm.comment.model.dto.NewCommentDto;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommentMapper {

    Comment convert(NewCommentDto newCommentDto);

    @Mapping(target = "authorName", source = "author.name")
    CommentDto convertToDto(Comment comment);

    Comment updateFromDto(NewCommentDto newCommentDto, @MappingTarget Comment comment);
}