package ru.practicum.ewm.compilation.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.CompilationService;
import ru.practicum.ewm.compilation.converter.CompilationMapper;
import ru.practicum.ewm.compilation.model.dto.CompilationDto;
import ru.practicum.ewm.compilation.model.dto.NewCompilationDto;
import ru.practicum.ewm.configs.Create;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationController {

    private final CompilationMapper compilationMapper;
    private final CompilationService compilationService;

    @PostMapping
    public CompilationDto create(@Validated({Create.class})
                                 @RequestBody NewCompilationDto compilationDto) {
        log.info("POST request: создание подборки событий {}", compilationDto);
        return compilationMapper.convertToDto(compilationService.create(compilationDto));
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEvent(@PathVariable Long compId,
                         @PathVariable Long eventId) {
        log.info("PATCH request: добавление в подборку {} события {}", compId, eventId);
        compilationService.addEvent(compId, eventId);
    }

    @PatchMapping("/{compId}/pin")
    public void pinCompilation(@PathVariable Long compId) {
        log.info("PATCH request: закрепление подборки id={}", compId);
        compilationService.pinCompilation(compId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpinCompilation(@PathVariable Long compId) {
        log.info("DELETE request: открепление подборки id={}", compId);
        compilationService.unpinCompilation(compId);
    }

    @DeleteMapping("/{compId}")
    public void delete(@PathVariable Long compId) {
        log.info("DELETE request: удалаение подборки id={}", compId);
        compilationService.delete(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEvent(@PathVariable Long compId,
                            @PathVariable Long eventId) {
        log.info("DELETE request: удалаение события {} из подборки id={}",eventId, compId);
        compilationService.deleteEvent(compId, eventId);
    }

}
