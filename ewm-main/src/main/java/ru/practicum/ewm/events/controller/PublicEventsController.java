package ru.practicum.ewm.events.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.events.dto.*;
import ru.practicum.ewm.events.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.UtilityClass.pattern;

@RestController
@RequiredArgsConstructor
public class PublicEventsController {

    private final EventService eventService;

    @GetMapping("/events")
    public List<EventsShortDto> getEvents( @RequestParam(required = false) String text,
                                           @RequestParam(required = false) List<Long> categories,
                                           @RequestParam(required = false) Boolean paid,
                                           @RequestParam(required = false) @DateTimeFormat(pattern = pattern) LocalDateTime rangeStart,
                                           @RequestParam(required = false) @DateTimeFormat(pattern = pattern) LocalDateTime rangeEnd,
                                           @RequestParam(required = false, defaultValue = "false") Boolean onlyAvailable,
                                           @RequestParam(required = false) String sort,
                                           @RequestParam(required = false, defaultValue = "0") Integer from,
                                           @RequestParam(required = false, defaultValue = "10") Integer size,
                                           HttpServletRequest request) {
        return eventService.getPublicEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from,
                size, request);
    }

    @GetMapping("/events/{eventId}")
    public EventFullDto getEventById(@PathVariable("eventId") Long eventId,
                                     HttpServletRequest request) {
       return eventService.getPublicEventById(eventId, request);
    }
}
