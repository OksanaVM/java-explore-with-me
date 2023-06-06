package ru.practicum.ewm.requests.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.requests.dto.NewRequestUpdateDto;
import ru.practicum.ewm.requests.dto.RequestDto;
import ru.practicum.ewm.requests.dto.RequestUpdateDto;
import ru.practicum.ewm.requests.service.RequestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PrivateRequestsController {
    private final RequestService requestService;

    @PostMapping("/users/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto create(@PathVariable("userId") Long userId,
                             @RequestParam(value = "eventId") Long eventId) {
        return requestService.create(userId, eventId);
    }

    @GetMapping("/users/{userId}/requests")
    public List<RequestDto> getRequestsById(@PathVariable("userId") Long userId) {
        return requestService.getRequestsById(userId);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<RequestDto> getRequestsByUserIdAndEventId(@PathVariable("userId") Long userId,
                                                          @PathVariable("eventId") Long eventId) {
        return requestService.getRequestsByUserIdAndEventId(userId, eventId);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable("userId") Long userId,
                                    @PathVariable(value = "requestId") Long requestId) {
        return requestService.cancelRequest(userId, requestId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public RequestUpdateDto updateRequestStatus(@PathVariable("userId") Long userId,
                                                @PathVariable("eventId") Long eventId,
                                                @RequestBody(required = false) NewRequestUpdateDto newRequestUpdateDto) {
        return requestService.updateRequestStatus(userId, eventId, newRequestUpdateDto);
    }
}
