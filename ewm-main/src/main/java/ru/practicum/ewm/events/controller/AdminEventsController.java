package ru.practicum.ewm.events.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.events.dto.*;
import ru.practicum.ewm.events.service.EventService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.UtilityClass.pattern;

@RestController
@RequiredArgsConstructor
public class AdminEventsController {

    private final EventService eventService;

    @PatchMapping("/admin/events/{eventId}")
    public EventFullDto updateEventByUserIdAndEventId(@PathVariable("eventId") Long eventId,
                                                      @RequestBody @Valid UpdateEvent dto) {
        return eventService.updateEventByEventId(eventId, dto);
    }

    @GetMapping("/admin/events")
    public List<EventFullDto> getEvents(@RequestParam(required = false) List<Long> users,
                                        @RequestParam(required = false) List<String> states,
                                        @RequestParam(required = false) List<Long> categories,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = pattern)
                                            LocalDateTime rangeStart,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = pattern)
                                            LocalDateTime rangeEnd,
                                        @RequestParam(required = false, defaultValue = "0") Integer from,
                                        @RequestParam(required = false, defaultValue = "10") Integer size) {
        return eventService.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }
}
