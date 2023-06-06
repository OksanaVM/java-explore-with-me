package ru.practicum.ewm.events.service;

import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.events.dto.EventDto;
import ru.practicum.ewm.events.dto.NewEventDto;
import ru.practicum.ewm.events.dto.SortVariant;
import ru.practicum.ewm.events.dto.State;
import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.users.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    EventDto create(NewEventDto newEventDto, LocationDto locationDto, UserDto userDto, CategoryDto categoryDto);

    List<EventDto> getEventsByUserId(Long userId, Integer from, Integer size);

    EventDto getEventByUserIdAndEventId(Long userId, Long eventId);

    EventDto updateEventByUserIdAndEventId(Long userId, Long eventId, NewEventDto newEventDto);

    EventDto updateEventByEventId(Long eventId, NewEventDto newEventDto);

    List<EventDto> getEvents(LocalDateTime rangeStart, LocalDateTime rangeEnd, List<Long> users,
                             List<State> states, List<Long> categories, Integer from, Integer size);

    List<EventDto> getPublicEvents(Integer from, Integer size, State state,
                                   String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                   LocalDateTime rangeEnd, SortVariant sort, Boolean onlyAvailable, String ip, String uri);

    EventDto getPublicEventById(Long eventId, String ip, String url);
}
