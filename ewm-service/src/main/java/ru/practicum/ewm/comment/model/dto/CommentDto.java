package ru.practicum.ewm.comment.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto {

    private Long id;

    private String text;

    private String authorName;

    private boolean hasReplies;

    private LocalDateTime created;
}
