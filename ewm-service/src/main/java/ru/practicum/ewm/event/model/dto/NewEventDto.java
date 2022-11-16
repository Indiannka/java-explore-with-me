package ru.practicum.ewm.event.model.dto;

import lombok.*;
import ru.practicum.ewm.configs.Create;
import ru.practicum.ewm.event.model.Location;
import ru.practicum.ewm.validation.EventCreationDate;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class NewEventDto {

    @Size(min = 20, max = 2000)
    @NotNull(message = "Поле annotation не должно быть пустым", groups = {Create.class})
    private String annotation;

    private Long categoryId;

    @Size(min = 20, max = 2000)
    private String description;

    @EventCreationDate (groups = {Create.class})
    @NotBlank(message = "Поле location не должно быть пустым", groups = {Create.class})
    private LocalDateTime eventDate;

    @NotNull(message = "Поле location не должно быть пустым", groups = {Create.class})
    private Location location;

    private Boolean paid;

    @PositiveOrZero
    private Integer participantLimit;

    private Boolean requestModeration;

    @Size(min = 3, max = 120)
    private String title;
}