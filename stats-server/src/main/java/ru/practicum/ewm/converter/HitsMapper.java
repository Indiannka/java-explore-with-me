package ru.practicum.ewm.converter;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.model.EndpointHit;
import ru.practicum.ewm.model.Hit;

@Mapper(componentModel = "spring")
public interface HitsMapper {

    @Mapping(target = "timestamp", source = "timestamp", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Hit convert(EndpointHit endpointHit);
}