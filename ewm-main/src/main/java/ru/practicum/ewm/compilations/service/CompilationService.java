package ru.practicum.ewm.compilations.service;

import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.compilations.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {

    CompilationDto createCompilation(NewCompilationDto dto);

    void deleteCompilation(Long compId);

    CompilationDto updateCompilations(Long compId, UpdateCompilationRequest dto);

    CompilationDto getCompilation(Long compId);

    List<CompilationDto> getCompilationsByFilters(Boolean pinned, Integer from, Integer size);
}
