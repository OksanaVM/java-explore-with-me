package ru.practicum.category.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.category.dto.NewCategoryDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryMapper {
    public static NewCategoryDto toCategoryDto(Category category) {
        return NewCategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category toCategory(NewCategoryDto newCategoryDto) {
        return Category.builder()
                .id(newCategoryDto.getId())
                .name(newCategoryDto.getName())
                .build();
    }
}
