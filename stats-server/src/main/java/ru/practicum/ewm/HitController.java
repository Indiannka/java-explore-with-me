package ru.practicum.ewm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.EndpointHit;
import ru.practicum.ewm.model.StatsView;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class HitController {

    private final HitService hitService;

    @PostMapping("/hit")
    public void saveStats(@RequestBody EndpointHit endpointHit) {
        log.info("POST request: Сохранение статистики с параметрами {}", endpointHit);
        hitService.saveStats(endpointHit);
    }

    @GetMapping("/stats")
    public Collection<StatsView> getStats(@RequestParam @NotNull String start,
                                          @RequestParam @NotNull String end,
                                          @RequestParam(required = false) String[] uris,
                                          @RequestParam(required = false, defaultValue = "false") Boolean unique) {
        log.info("GET request: запрос статистики с параметрами {}, {}, {}, {}", start, end, uris, unique);
        return  hitService.getStats(start, end, uris, unique);
    }
}