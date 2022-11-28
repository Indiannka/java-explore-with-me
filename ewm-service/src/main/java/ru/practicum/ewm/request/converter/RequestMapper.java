package ru.practicum.ewm.request.converter;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.dto.RequestDto;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    @Mapping(target = "event", source = "event.id")
    @Mapping(target = "requester", source = "requester.id")
    @Mapping(target = "created", source = "createdOn")
    RequestDto convertToDto(Request request);
}