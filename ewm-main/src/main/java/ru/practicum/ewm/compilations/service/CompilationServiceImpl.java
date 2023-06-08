package ru.practicum.ewm.compilations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.compilations.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilations.model.Compilation;
import ru.practicum.ewm.compilations.repository.CompilationRepository;
import ru.practicum.ewm.events.dto.EventsShortDto;
import ru.practicum.ewm.events.model.Event;
import ru.practicum.ewm.events.repository.EventRepository;
import ru.practicum.ewm.events.service.EventMapper;
import ru.practicum.ewm.exeption.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static ru.practicum.ewm.compilations.service.MapperCompilation.toCompilation;
import static ru.practicum.ewm.compilations.service.MapperCompilation.toCompilationDto;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    /**
     * Добавление новой подборки(может не содержать событий)
     */
    @Transactional
    public CompilationDto createCompilation(NewCompilationDto dto) {
        if (dto.getPinned() == null) {
            dto.setPinned(false);
        }
        Compilation compilation;
        List<Event> eventList = new ArrayList<>();
        List<EventsShortDto> eventsShortDtos = new ArrayList<>();
        if (dto.getEvents() != null) {
            eventList = eventRepository.findAllById(dto.getEvents());
            eventsShortDtos = eventList.stream()
                    .map(EventMapper::toEventShortDto)
                    .collect(Collectors.toList());
        }
        compilation = toCompilation(dto, eventList);
        Compilation newCompilation = compilationRepository.save(compilation);
        return toCompilationDto(newCompilation, eventsShortDtos);
    }

    /**
     * Удаление подборки
     */
    @Transactional
    public void deleteCompilation(Long compId) {
        compilationRepository.deleteById(compId);
    }

    /**
     * Обновить информацию о подборке
     */
    @Transactional
    public CompilationDto updateCompilations(Long compId, UpdateCompilationRequest dto) {
        List<Event> eventList;
        List<EventsShortDto> eventsShortDtos;
        Compilation compilation = findCompilationById(compId);
        if (dto.getEvents() != null) {
            eventList = eventRepository.findAllById(dto.getEvents());
            eventsShortDtos = eventList.stream()
                    .map(EventMapper::toEventShortDto)
                    .collect(Collectors.toList());
            compilation.setEvents(eventList);
        } else {
            eventList = compilation.getEvents();
            eventsShortDtos = eventList.stream()
                    .map(EventMapper::toEventShortDto)
                    .collect(Collectors.toList());
        }
        ofNullable(dto.getPinned()).ifPresent(compilation::setPinned);
        ofNullable(dto.getTitle()).ifPresent(compilation::setTitle);
        Compilation newCompilation = compilationRepository.save(compilation);
        return toCompilationDto(newCompilation, eventsShortDtos);
    }

    /**
     * Получение подборки событий по её id
     */
    public CompilationDto getCompilation(Long compId) {
        return toCompilationDto(findCompilationById(compId));
    }

    /**
     * Получение подборок событий
     */
    public List<CompilationDto> getCompilationsByFilters(Boolean pinned, Integer from, Integer size) {
        PageRequest page = PageRequest.of(from, size);
        List<Compilation> compilations = compilationRepository.findByPinned(pinned, page);
        return compilations.stream()
                .map(MapperCompilation::toCompilationDto)
                .collect(Collectors.toList());
    }

    private Compilation findCompilationById(Long compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Подборка с таким id  не найдена"));
    }
}
