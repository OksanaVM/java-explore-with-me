package ru.practicum.compilation.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.event.dto.EventsShortDto;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.MapperEvent;

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
                        .map(MapperEvent::toEventShortDto)
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
