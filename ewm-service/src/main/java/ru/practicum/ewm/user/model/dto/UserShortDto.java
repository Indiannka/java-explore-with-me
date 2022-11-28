package ru.practicum.ewm.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class UserShortDto {
    private Long id;
    private String name;
}