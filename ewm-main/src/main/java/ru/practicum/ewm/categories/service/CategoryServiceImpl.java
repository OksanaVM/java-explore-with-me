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
import ru.practicum.ewm.exeption.ConflictException;
import ru.practicum.ewm.exeption.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final EventRepository eventRepository;

    private static final String CATEGORY_NOT_FOUND_MESSAGE = "Category with id=%s was not found";

    @Override
    @Transactional
    public NewCategoryDto create(NewCategoryDto newCategoryDto) {
        Category category = CategoryMapper.toCategory(newCategoryDto);

        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewCategoryDto> getCategories(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);

        return categoryRepository.findAll(pageable).stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public NewCategoryDto getCategoryById(Long categoryId, Integer from, Integer size) {
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
            throw new ConflictException("Нельзя удалить категорию. Существуют события, связанные с категорией.");
        }

        Integer integer = categoryRepository.deleteCategoryById(categoryId);

        if (integer == 0) {
            throw new NotFoundException(String.format(CATEGORY_NOT_FOUND_MESSAGE, categoryId));
        }
    }

    @Override
    @Transactional
    public NewCategoryDto updateCategoryById(Long categoryId, NewCategoryDto newCategoryDto) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> {
            throw new NotFoundException(String.format(CATEGORY_NOT_FOUND_MESSAGE, categoryId));
        });

        category.setName(newCategoryDto.getName());

        return CategoryMapper.toCategoryDto(category);
    }
}

