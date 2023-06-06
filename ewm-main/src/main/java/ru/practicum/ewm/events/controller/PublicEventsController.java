package ru.practicum.ewm.events.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.events.dto.EventDto;
import ru.practicum.ewm.events.dto.SortVariant;
import ru.practicum.ewm.events.dto.State;
import ru.practicum.ewm.events.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PublicEventsController {

    private final EventService eventService;

    @GetMapping("/events")
    public List<EventDto> getEvents(@RequestParam(value = "text", required = false)
                                    String text,
                                    @RequestParam(value = "categories", required = false)
                                    List<Long> categories,
                                    @RequestParam(value = "paid", required = false)
                                    Boolean paid,
                                    @RequestParam(value = "rangeStart", required = false)
                                    LocalDateTime rangeStart,
                                    @RequestParam(value = "rangeEnd", required = false)
                                    LocalDateTime rangeEnd,
                                    @RequestParam(value = "paid", defaultValue = "false")
                                    Boolean onlyAvailable,
                                    @RequestParam(value = "sort", required = false) SortVariant sort,
                                    @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
                                    @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                    HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String url = request.getRequestURI();

        return eventService.getPublicEvents(from,
                size,
                State.PUBLISHED,
                text,
                categories,
                paid,
                rangeStart,
                rangeEnd,
                sort,
                onlyAvailable,
                ip,
                url);
    }

    @GetMapping("/events/{eventId}")
    public EventDto getEventById(@PathVariable("eventId") Long eventId,
                                 HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String url = request.getRequestURI();

        return eventService.getPublicEventById(eventId, ip, url);
    }
}
