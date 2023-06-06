package ru.practicum.ewm.categories.service;

import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.categories.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto create(NewCategoryDto newCategoryDto);

    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategoryById(Long categoryId, Integer from, Integer size);

    void deleteCategoryById(Long categoryId);

    CategoryDto updateCategoryById(Long categoryId, NewCategoryDto newCategoryDto);
}
