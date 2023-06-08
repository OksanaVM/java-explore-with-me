package ru.practicum.request.dto;

import lombok.Builder;
import lombok.Getter;
import ru.practicum.request.dto.ParticipationRequestDto;

import java.util.List;

@Getter
@Builder
public class EventRequestStatusUpdateResult {
    private List<ParticipationRequestDto> confirmedRequests;
    private List<ParticipationRequestDto> rejectedRequests;
}
