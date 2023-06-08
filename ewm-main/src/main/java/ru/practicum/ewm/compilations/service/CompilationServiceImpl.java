package ru.practicum.ewm.compilations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilations.model.Compilation;
import ru.practicum.ewm.compilations.repository.CompilationRepository;
import ru.practicum.ewm.events.model.Event;
import ru.practicum.ewm.events.repository.EventRepository;
import ru.practicum.ewm.exeption.NotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    private static final String COMPILATION_NOT_FOUND_MESSAGE = "Compilation with id=%s was not found";

    @Override
    @Transactional(readOnly = true)
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);

        return CompilationMapper
                .toCompilationDto(compilationRepository.getCompilations(pinned, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public CompilationDto getCompilationById(Long compId) {
        Compilation compilation = compilationRepository.findCompilationById(compId)
                .orElseThrow(() -> new NotFoundException(String.format(COMPILATION_NOT_FOUND_MESSAGE, compId)));

        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    @Transactional
    public CompilationDto create(NewCompilationDto newCompilationDto) {
        Compilation compilation = compilationRepository
                .save(CompilationMapper.toCompilation(newCompilationDto));
        List<Event> events = eventRepository.findAllById(newCompilationDto.getEvents());

        compilation.getEvents().addAll(events);

        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    @Transactional
    public CompilationDto update(Long compId, NewCompilationDto newCompilationDto) {
        Compilation compilation = compilationRepository.findCompilationById(compId)
                .orElseThrow(() -> new NotFoundException(String.format(COMPILATION_NOT_FOUND_MESSAGE, compId)));
        List<Event> events = eventRepository.findAllById(newCompilationDto.getEvents());

        compilation.setTitle(Objects.requireNonNullElse(newCompilationDto.getTitle(),
                compilation.getTitle()));
        compilation.setPinned(Objects.requireNonNullElse(newCompilationDto.getPinned(),
                compilation.getPinned()));
        compilation.setEvents(new HashSet<>(events));

        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    @Transactional
    public void delete(Long compId) {
        Integer integer = compilationRepository.deleteCompilationById(compId);

        if (integer == 0) {
            throw new NotFoundException(String.format(COMPILATION_NOT_FOUND_MESSAGE, compId));
        }
    }
}
