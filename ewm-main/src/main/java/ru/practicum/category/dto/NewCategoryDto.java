package ru.practicum.category.dto;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class NewCategoryDto {
    private Long id;
    @NotBlank
    @Length(max = 50)
    private String name;
}
