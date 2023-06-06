package ru.practicum.ewm.requests.service;

import ru.practicum.ewm.events.dto.State;
import ru.practicum.ewm.requests.dto.RequestDto;
import ru.practicum.ewm.requests.model.Request;

import java.util.List;
import java.util.stream.Collectors;

public class RequestMapper {

    private RequestMapper() {
    }

    public static RequestDto toRequestDto(Request request) {
        return new RequestDto(
                request.getId(),
                request.getEventId(),
                request.getRequesterId(),
                request.getStatus(),
                request.getCreated());
    }

    public static List<RequestDto> toRequestDto(List<Request> requests) {
        return requests
                .stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    public static Request toRequest(Long eventId,
                                    Long requesterId,
                                    State status) {
        Request request = new Request();

        request.setRequesterId(requesterId);
        request.setEventId(eventId);
        request.setStatus(status);

        return request;
    }
}
