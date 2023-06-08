package ru.practicum.ewm.requests.service;

import ru.practicum.ewm.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {

    ParticipationRequestDto createRequest(Long userId, Long requestDto);

    List<ParticipationRequestDto> getRequestsForUser(Long userId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);
}
