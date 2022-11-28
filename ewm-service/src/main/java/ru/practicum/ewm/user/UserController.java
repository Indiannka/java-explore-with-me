package ru.practicum.ewm.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.configs.Create;
import ru.practicum.ewm.user.converter.UserMapper;
import ru.practicum.ewm.user.model.dto.UserDto;

import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userConverter;

    @GetMapping
    public Collection<UserDto> getUsers(@RequestParam (defaultValue = "0", required = false) @Min(0)  int from,
                                        @RequestParam (defaultValue = "10", required = false) @Min(1) int size,
                                        @RequestParam (required = false) Long[] ids) {
        log.info("GET request: запрос пользователtq с id={}", ids);
        return userService.getUsers(ids, from, size).stream()
                .map(userConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public UserDto create(@Validated({Create.class})
                          @RequestBody UserDto userDto) {
        log.info("POST request: создание пользователя id={}", userDto.toString());
        return userConverter.convertToDto(userService.create(userDto));
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") long userId) {
        log.info("DELETE request: удаление пользователя с id={}", userId);
        userService.delete(userId);
    }
}