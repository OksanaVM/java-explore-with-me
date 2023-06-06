package ru.practicum.ewm.requests.service;

import ru.practicum.ewm.requests.dto.NewRequestUpdateDto;
import ru.practicum.ewm.requests.dto.RequestDto;
import ru.practicum.ewm.requests.dto.RequestUpdateDto;

import java.util.List;

public interface RequestService {

    RequestDto create(Long userId, Long eventId);

    List<RequestDto> getRequestsById(Long userId);

    List<RequestDto> getRequestsByUserIdAndEventId(Long userId, Long eventId);

    RequestUpdateDto updateRequestStatus(Long userId, Long eventId, NewRequestUpdateDto newRequestUpdateDto);

    RequestDto cancelRequest(Long userId, Long eventId);
}
