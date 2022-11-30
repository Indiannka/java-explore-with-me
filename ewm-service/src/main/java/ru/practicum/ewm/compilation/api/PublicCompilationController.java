package ru.practicum.ewm.compilation.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.CompilationService;
import ru.practicum.ewm.compilation.converter.CompilationMapper;
import ru.practicum.ewm.compilation.model.dto.CompilationDto;

import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class PublicCompilationController {

    private final CompilationMapper compilationMapper;
    private final CompilationService compilationService;

    @GetMapping
    public Collection<CompilationDto> getAll(@RequestParam (required = false) Boolean pinned,
                                              @RequestParam (defaultValue = "0", required = false) @Min(0)  int from,
                                              @RequestParam (defaultValue = "10", required = false) @Min(1) int size) {
        log.info("GET request: запрос всех подборок");
        return compilationService.getAll(pinned, PageRequest.of(from / size, size)).stream()
                .map(compilationMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{compId}")
    public CompilationDto getById(@PathVariable Long compId) {
        log.info("GET request: запрос подборки id={}", compId);
        return compilationMapper.convertToDto(compilationService.getById(compId));
    }
}