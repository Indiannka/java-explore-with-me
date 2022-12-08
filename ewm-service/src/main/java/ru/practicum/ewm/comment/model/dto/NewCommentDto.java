package ru.practicum.ewm.comment.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class NewCommentDto {

    @NotBlank(message = "Поле text не должно быть пустым")
    @Size(min = 2, max = 4000)
    private String text;
}