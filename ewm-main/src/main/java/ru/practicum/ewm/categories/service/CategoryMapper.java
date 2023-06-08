package ru.practicum.ewm.categories.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.categories.dto.NewCategoryDto;
import ru.practicum.ewm.categories.model.Category;

import java.util.List;
import java.util.stream.Collectors;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryMapper {



//    public static CategoryDto toCategoryDto(Category category) {
//        return new CategoryDto(category.getId(), category.getName());
//    }
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

