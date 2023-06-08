package ru.practicum.compilation.dto;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import ru.practicum.event.dto.EventsShortDto;

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
