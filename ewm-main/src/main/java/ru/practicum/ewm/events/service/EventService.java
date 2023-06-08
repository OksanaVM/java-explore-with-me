package ru.practicum.ewm.events.service;

import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.events.dto.*;
import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.users.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    EventFullDto create(Long userId, NewEventDto newEventDto);

    List<EventsShortDto> getEventsByUserId(Long userId, Integer from, Integer size);

    EventFullDto getEventByUserIdAndEventId(Long userId, Long eventId);

    EventFullDto updateEventByUserIdAndEventId(Long userId, Long eventId, UpdateEvent dto);

    EventFullDto updateEventByEventId(Long eventId, UpdateEvent dto);

    List<EventFullDto> getEvents(List<Long> users, List<String> states, List<Long> categories,
                             LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    List<EventsShortDto> getPublicEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                   LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from,
                                   Integer size, HttpServletRequest request);

    EventFullDto getPublicEventById(Long eventId, HttpServletRequest request);
}
