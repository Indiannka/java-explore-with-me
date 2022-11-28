package ru.practicum.ewm.compilation;


import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.model.dto.NewCompilationDto;

import java.util.Collection;

public interface CompilationService {

    Collection<Compilation> getAll(Boolean pinned, int from, int size);

    Compilation getById(Long compId);

    Compilation create(NewCompilationDto compilationDto);

    void addEvent(Long compId, Long eventId);

    void pinCompilation(Long compId);

    void unpinCompilation(Long compId);

    void delete(Long compId);

    void deleteEvent(Long compId, Long eventId);
}