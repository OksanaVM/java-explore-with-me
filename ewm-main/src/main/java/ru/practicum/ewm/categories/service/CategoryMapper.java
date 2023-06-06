package ru.practicum.ewm.categories.service;

import org.springframework.data.domain.Page;
import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.categories.dto.NewCategoryDto;
import ru.practicum.ewm.categories.model.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {

    private CategoryMapper() {
    }

    public static Category toCategory(NewCategoryDto newCategoryDto) {
        Category category = new Category();

        category.setName(newCategoryDto.getName());

        return category;
    }

    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public static List<CategoryDto> toCategoryDto(Page<Category> categories) {
        return categories
                .stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }
}

