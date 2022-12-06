package ru.practicum.ewm.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.converter.RequestMapper;
import ru.practicum.ewm.request.model.dto.RequestDto;

import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class RequestController {

    private final RequestMapper requestMapper;
    private final RequestService requestService;

    @PostMapping("/{userId}/requests")
    public RequestDto create(@PathVariable Long userId,
                             @RequestParam @Positive Long eventId) {
        return requestMapper.convertToDto(requestService.create(userId, eventId));
    }

    @GetMapping("/{userId}/requests")
    public Collection<RequestDto> getAll(@PathVariable Long userId) {
        return requestService.getAll(userId).stream()
                .map(requestMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public RequestDto cancel(@PathVariable Long userId,
                             @PathVariable Long requestId) {
        return requestMapper.convertToDto(requestService.cancel(userId, requestId));
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public Collection<RequestDto> getRequestsByEventOwner(@PathVariable Long userId,
                                                          @PathVariable Long eventId) {
        log.info("GET request: запрос заявок на участии в событии {} пользователя {}",eventId, userId);
        return requestService.getRequestsByEventOwner(userId, eventId).stream()
                .map(requestMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public RequestDto confirmByEventOwner(@PathVariable Long userId,
                                          @PathVariable Long eventId,
                                          @PathVariable("reqId") Long requestId) {
        return requestMapper.convertToDto(requestService.confirmByEventOwner(userId, eventId, requestId));
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public RequestDto cancelByEventOwner(@PathVariable Long userId,
                                         @PathVariable Long eventId,
                                         @PathVariable("reqId") Long requestId) {
        return requestMapper.convertToDto(requestService.rejectByEventOwner(userId, eventId, requestId));
    }
}