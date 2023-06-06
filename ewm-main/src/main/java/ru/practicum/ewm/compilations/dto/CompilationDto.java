package ru.practicum.ewm.compilations.dto;

import lombok.Value;
import ru.practicum.ewm.events.dto.EventDto;

import java.util.List;

@Value
public class CompilationDto {
    Long id;
    Boolean pinned;
    String title;
    List<EventDto> events;
}

