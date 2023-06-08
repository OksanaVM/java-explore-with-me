package ru.practicum.ewm.compilations.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.compilations.model.Compilation;
import ru.practicum.ewm.events.dto.EventsShortDto;
import ru.practicum.ewm.events.model.Event;
import ru.practicum.ewm.events.service.EventMapper;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapperCompilation {


    public static CompilationDto toCompilationDto(Compilation compilation, List<EventsShortDto> eventsShortDtos) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .events(eventsShortDtos)
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .build();
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .events(compilation.getEvents().stream()
                        .map(EventMapper::toEventShortDto)
                        .collect(Collectors.toList()))
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .build();
    }

    public static Compilation toCompilation(NewCompilationDto dto, List<Event> eventList) {
        return Compilation.builder()
                .events(eventList)
                .pinned(dto.getPinned())
                .title(dto.getTitle())
                .build();
    }
}
