package ru.practicum.ewm.event.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.EventService;
import ru.practicum.ewm.event.converter.EventMapper;
import ru.practicum.ewm.event.model.dto.AdminUpdateEventDto;
import ru.practicum.ewm.event.model.dto.EventFullDto;
import ru.practicum.ewm.event.model.dto.SearchParameters;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.configs.Constants.DATE_FORMAT;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventController {

    private final EventMapper eventMapper;
    private final EventService eventService;

    @PutMapping("/{eventId}")
    public EventFullDto update(@RequestBody AdminUpdateEventDto eventDto,
                               @PathVariable Long eventId) {
        log.info("POST request: редактирование события {}", eventId);
        return eventMapper.convertToFullDto(eventService.updateAdmin(eventDto, eventId));
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publish(@PathVariable Long eventId) {
        log.info("PATCH request: публикация события id={} администратором ", eventId);
        return eventMapper.convertToFullDto(eventService.publish(eventId));
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto reject(@PathVariable Long eventId) {
        log.info("PATCH request: отказ администратора в публикации события id={}", eventId);
        return eventMapper.convertToFullDto(eventService.reject(eventId));
    }

    @GetMapping()
    public Collection<EventFullDto> getAllAdmin(@RequestParam(value = "users", required = false) List<Long> users,
                                                @RequestParam(value = "states", required = false) List<String> states,
                                                @RequestParam(value = "categories", required = false) List<Long> categories,
                                                @RequestParam(value = "rangeStart", required = false) @DateTimeFormat(pattern = DATE_FORMAT)
                                                LocalDateTime rangeStart,
                                                @RequestParam(value = "rangeEnd", required = false) @DateTimeFormat(pattern = DATE_FORMAT)
                                                LocalDateTime rangeEnd,
                                                @RequestParam (value = "from", defaultValue = "0", required = false) @Min(0)  int from,
                                                @RequestParam (value = "size", defaultValue = "10", required = false) @Min(1) int size,
                                                HttpServletRequest request) {
        SearchParameters parameters = SearchParameters.builder()
                .users(users)
                .states(states)
                .categories(categories)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .page(from / size)
                .size(size)
                .build();
        log.info("GET request: запрос событий с парметрами {}", parameters);
        return eventService.getAllAdmin(parameters).stream()
                .map(eventMapper::convertToFullDto)
                .collect(Collectors.toList());
    }
}