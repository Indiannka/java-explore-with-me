package ru.practicum.ewm.event.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.ewm.category.CategoryService;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.dto.*;
import ru.practicum.ewm.user.converter.UserMapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CategoryService.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventMapper {

    @Mapping(target = "category", source = "category")
    @Mapping(target = "lon", source = "location.lon")
    @Mapping(target = "lat", source = "location.lat")
    @Mapping(target = "state", constant = "PENDING")
    @Mapping(target = "confirmedRequests", constant = "0")
    @Mapping(target = "participantLimit", source = "participantLimit", defaultValue = "0")
    @Mapping(target = "requestModeration", source = "requestModeration", defaultValue = "true")
    @Mapping(target = "paid", source = "paid", defaultValue = "false")
    Event convert(NewEventDto newEventDto);

    @Mapping(target = "location.lon", source = "lon")
    @Mapping(target = "location.lat", source = "lat")
    EventFullDto convertToFullDto(Event event);

    EventShortDto convertToShortDto(Event event);

    @Mapping(target = "event.id", source = "eventId")
    void updateFromEventDto(UpdateEventRequest updateEventRequest, @MappingTarget Event event);

    Event updateFromAdminDto(AdminUpdateEventDto adminUpdateEventRequest, @MappingTarget Event event);
}