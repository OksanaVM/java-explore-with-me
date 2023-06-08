package ru.practicum.ewm.compilations.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.events.dto.EventDto;
import ru.practicum.ewm.events.dto.EventsShortDto;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Builder
public class CompilationDto {
    private Long id;
    private List<EventsShortDto> events;
    private Boolean pinned;
    @NotBlank
    @Length(max = 50)
    private String title;
}

