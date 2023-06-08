package ru.practicum.compilation.dto;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Builder
public class UpdateCompilationRequest {
    private List<Long> events;
    private Boolean pinned;
    @Length(max = 50)
    private String title;
}
