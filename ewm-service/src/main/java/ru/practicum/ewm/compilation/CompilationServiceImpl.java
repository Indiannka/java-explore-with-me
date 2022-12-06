package ru.practicum.ewm.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilation.converter.CompilationMapper;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.model.dto.NewCompilationDto;
import ru.practicum.ewm.event.EventRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.exceptions.NotFoundException;

import java.util.Collection;

import static ru.practicum.ewm.configs.Constants.COMPILATION;
import static ru.practicum.ewm.configs.Constants.EVENT;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    private final EventRepository eventRepository;

    @Override
    @Transactional(readOnly = true)
    public Collection<Compilation> getAll(Boolean pinned, Pageable pageable) {
        return compilationRepository.findAllByPinned(pinned, pageable).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Compilation getById(Long compId) {
        return compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException(COMPILATION, compId));
    }

    @Override
    @Transactional
    public Compilation create(NewCompilationDto compilationDto) {
        return compilationRepository.save(compilationMapper.convert(compilationDto));
    }

    @Override
    @Transactional
    public void addEvent(Long compId, Long eventId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException(COMPILATION, compId));
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException(EVENT,eventId));
        compilation.getEvents().add(event);
        compilationRepository.save(compilation);
    }

    @Override
    @Transactional
    public void pinCompilation(Long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException(COMPILATION, compId));
        compilation.setPinned(true);
        compilationRepository.save(compilation);
    }

    @Override
    @Transactional
    public void unpinCompilation(Long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException(COMPILATION, compId));
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }

    @Override
    @Transactional
    public void delete(Long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    @Transactional
    public void deleteEvent(Long compId, Long eventId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException(COMPILATION, compId));
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException(EVENT,eventId));
        compilation.getEvents().remove(event);
        compilationRepository.save(compilation);
    }
}