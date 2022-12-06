package ru.practicum.ewm.event.model;

import lombok.*;

@Getter
@EqualsAndHashCode
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Location {
    private Float lat;
    private Float lon;
}