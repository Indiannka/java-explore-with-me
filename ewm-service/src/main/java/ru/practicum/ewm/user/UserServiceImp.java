package ru.practicum.ewm.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.user.converter.UserMapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.model.dto.UserDto;

import java.util.Arrays;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserServiceImp  implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userConverter;

    @Override
    @Transactional
    public User create(UserDto userDto) {
        User user = userConverter.convert(userDto);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<User> getUsers(Long[] ids, int from, int size) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        if (ids == null) {
            return userRepository.findAll(pageable).toList();
        }
        return userRepository.findAllByIdIn(Arrays.asList(ids), pageable).toList();
    }
}