package ru.practicum.ewm.compilations.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Value
public class NewCompilationDto {
    @NotNull
    Boolean pinned;
    @NotBlank
    String title;
    List<Long> events;
}