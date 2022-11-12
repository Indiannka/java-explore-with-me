package ru.practicum.ewm.user;

import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.model.dto.UserDto;

import java.util.Collection;

public interface UserService {

    User create(UserDto userDto);

    void delete(long userId);

    Collection<User> getUsers(Long[] ids, int from, int size);
}
