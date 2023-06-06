package ru.practicum.ewm.compilations.service;

import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {

    List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDto getCompilationById(Long compId);

    CompilationDto create(NewCompilationDto newCompilationDto);

    CompilationDto update(Long compId, NewCompilationDto compilationDto);

    void delete(Long compId);
}
