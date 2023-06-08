package ru.practicum.ewm.categories.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Value
@Builder
@AllArgsConstructor
@Jacksonized
public class NewCategoryDto {
    private Long id;
    @NotBlank
    @Length(max = 50)
    private String name;
}

