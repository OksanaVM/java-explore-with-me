package ru.practicum.ewm.categories.service;

import ru.practicum.ewm.categories.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    NewCategoryDto create(NewCategoryDto newCategoryDto);

    List<NewCategoryDto> getCategories(Integer from, Integer size);

    NewCategoryDto getCategoryById(Long categoryId, Integer from, Integer size);

    void deleteCategoryById(Long categoryId);

    NewCategoryDto updateCategoryById(Long categoryId, NewCategoryDto newCategoryDto);
}
