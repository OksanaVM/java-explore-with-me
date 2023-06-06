package ru.practicum.ewm.categories.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.categories.dto.NewCategoryDto;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.categories.repository.CategoryRepository;
import ru.practicum.ewm.events.repository.EventRepository;
import ru.practicum.ewm.exeption.NotFoundException;
import ru.practicum.ewm.exeption.OperationException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final EventRepository eventRepository;

    private static final String CATEGORY_NOT_FOUND_MESSAGE = "Category with id=%s was not found";

    @Override
    @Transactional
    public CategoryDto create(NewCategoryDto newCategoryDto) {
        Category category = CategoryMapper.toCategory(newCategoryDto);

        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);

        return CategoryMapper.toCategoryDto(categoryRepository.findAll(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long categoryId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        List<Category> categories = categoryRepository.findCategoryById(categoryId, pageable);

        if (categories.isEmpty()) {
            throw new NotFoundException(String.format(CATEGORY_NOT_FOUND_MESSAGE, categoryId));
        }

        return CategoryMapper.toCategoryDto(categories.get(0));
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long categoryId) {
        if (!eventRepository.findAllByCategoryId(categoryId).isEmpty()) {
            throw new OperationException("The category is not empty");
        }

        Integer integer = categoryRepository.deleteCategoryById(categoryId);

        if (integer == 0) {
            throw new NotFoundException(String.format(CATEGORY_NOT_FOUND_MESSAGE, categoryId));
        }
    }

    @Override
    @Transactional
    public CategoryDto updateCategoryById(Long categoryId, NewCategoryDto newCategoryDto) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> {
            throw new NotFoundException(String.format(CATEGORY_NOT_FOUND_MESSAGE, categoryId));
        });

        category.setName(newCategoryDto.getName());

        return CategoryMapper.toCategoryDto(category);
    }
}

