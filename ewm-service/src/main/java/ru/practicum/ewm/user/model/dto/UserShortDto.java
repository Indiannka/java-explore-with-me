package ru.practicum.ewm.user.model.dto;

import lombok.*;

@Getter
@EqualsAndHashCode
@Builder(toBuilder = true)
@AllArgsConstructor
public class UserShortDto {
    private Long id;
    private String name;
}