package ru.practicum.ewm.user.converter;

import org.mapstruct.Mapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.model.dto.UserDto;
import ru.practicum.ewm.user.model.dto.UserShortDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto convertToDto(User user);

    UserShortDto convertToShort(User user);

    User convert(UserDto userDto);
}