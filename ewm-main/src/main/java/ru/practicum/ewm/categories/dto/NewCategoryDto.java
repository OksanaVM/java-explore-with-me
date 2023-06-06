package ru.practicum.ewm.categories.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;

@Value
@Builder
@AllArgsConstructor
@Jacksonized
public class NewCategoryDto {
    @NotBlank
    String name;
}

