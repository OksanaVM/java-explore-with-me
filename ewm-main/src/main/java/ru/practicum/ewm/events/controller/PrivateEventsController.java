package ru.practicum.ewm.events.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.categories.service.CategoryService;
import ru.practicum.ewm.events.dto.*;
import ru.practicum.ewm.events.service.EventService;
import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.location.dto.NewLocationDto;
import ru.practicum.ewm.location.service.LocationService;
import ru.practicum.ewm.users.dto.UserDto;
import ru.practicum.ewm.users.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PrivateEventsController {

    private final EventService eventService;


    @PostMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@PathVariable("userId") Long userId,
                               @RequestBody @Valid NewEventDto newEventDto) {
         return eventService.create(userId, newEventDto);
    }

    @GetMapping("/users/{userId}/events")
    public List<EventsShortDto> getEventsByUserId(@PathVariable("userId") Long userId,
                                                  @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
                                                  @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return eventService.getEventsByUserId(userId, from, size);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto getEventByUserIdAndEventId(@PathVariable("userId") Long userId,
                                               @PathVariable("eventId") Long eventId) {
        return eventService.getEventByUserIdAndEventId(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventFullDto updateEventByUserIdAndEventId(@PathVariable("userId") Long userId,
                                                  @PathVariable("eventId") Long eventId,
                                                  @RequestBody @Valid UpdateEvent dto) {
        return eventService.updateEventByUserIdAndEventId(userId, eventId, dto);
    }
}
