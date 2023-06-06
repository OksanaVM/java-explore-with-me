package ru.practicum.ewm.compilations.service;

import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.compilations.model.Compilation;
import ru.practicum.ewm.events.service.EventMapper;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class CompilationMapper {

    private CompilationMapper() {
    }

    public static List<CompilationDto> toCompilationDto(List<Compilation> compilations) {
        return compilations.stream().map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(compilation.getId(),
                compilation.getPinned(),
                compilation.getTitle(),
                EventMapper.toEventDto(compilation.getEvents()));
    }

    public static Compilation toCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = new Compilation();

        compilation.setTitle(newCompilationDto.getTitle());
        compilation.setPinned(newCompilationDto.getPinned());
        compilation.setEvents(new HashSet<>());

        return compilation;
    }
}
