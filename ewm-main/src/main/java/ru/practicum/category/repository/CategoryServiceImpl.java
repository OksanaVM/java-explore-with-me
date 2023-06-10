package ru.practicum.category.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.model.Category;
import ru.practicum.category.model.CategoryMapper;
import ru.practicum.category.service.CategoryService;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventsRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static ru.practicum.category.model.CategoryMapper.toCategory;
import static ru.practicum.category.model.CategoryMapper.toCategoryDto;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventsRepository eventsRepository;

    /**
     * Добавление новой категории
     */
    @Transactional
    public NewCategoryDto createCategory(NewCategoryDto newCategoryDto) {
        Category category = toCategory(newCategoryDto);
        return toCategoryDto(categoryRepository.save(category));
    }

    /**
     * Удаление категории
     */
    @Transactional
    public void deleteCategory(Long id) {
        Category category = getCategoryModel(id);
        List<Event> events = eventsRepository.findByCategory(category);
        if (!events.isEmpty()) {
            throw new ConflictException("Нельзя удалить категорию. Существуют события, связанные с категорией.");
        }
        categoryRepository.deleteById(id);

    }

    /**
     * Изменение категории
     */
    @Transactional
    public NewCategoryDto updateCategory(Long id, NewCategoryDto newCategoryDto) {
        Category category = getCategoryModel(id);
        ofNullable(newCategoryDto.getName()).ifPresent(category::setName);
        return toCategoryDto(categoryRepository.save(category));
    }

    /**
     * Получение категорий
     */
    public List<NewCategoryDto> getCategories(Integer from, Integer size) {
        PageRequest page = PageRequest.of(from, size);
        return categoryRepository.findAll(page).stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    /**
     * Получение информации о категории по ее идентификатору
     */
    public NewCategoryDto getCategory(Long id) {
        Category category = getCategoryModel(id);
        return toCategoryDto(category);
    }
//    public NewCategoryDto getCategory(Long id) {
//        Category category = categoryRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("Пользователь с таким id  не найден"));
//        return toCategoryDto(category);
//    }

    private Category getCategoryModel(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Категории с таким id не найдено"));
    }

}
