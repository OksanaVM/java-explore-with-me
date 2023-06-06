package ru.practicum.ewm.events.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.events.dto.EventDto;
import ru.practicum.ewm.events.dto.NewEventDto;
import ru.practicum.ewm.events.dto.State;
import ru.practicum.ewm.events.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminEventsController {

    private final EventService eventService;

    @PatchMapping("/admin/events/{eventId}")
    public EventDto updateEventByUserIdAndEventId(@PathVariable("eventId") Long eventId,
                                                  @RequestBody(required = false) NewEventDto newEventDto) {
        return eventService.updateEventByEventId(eventId, newEventDto);
    }

    @GetMapping("/admin/events")
    public List<EventDto> getEvents(@RequestParam(value = "rangeStart", required = false)
                                    LocalDateTime rangeStart,
                                    @RequestParam(value = "rangeEnd", required = false)
                                    LocalDateTime rangeEnd,
                                    @RequestParam(value = "users", required = false)
                                    List<Long> users,
                                    @RequestParam(value = "states", required = false)
                                    List<State> states,
                                    @RequestParam(value = "categories", required = false)
                                    List<Long> categories,
                                    @RequestParam(value = "from", required = false, defaultValue = "0")
                                    Integer from,
                                    @RequestParam(value = "size", required = false, defaultValue = "10")
                                    Integer size) {
        return eventService.getEvents(rangeStart, rangeEnd, users, states, categories, from, size);
    }
}
