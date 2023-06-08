package ru.practicum.ewm.requests.dto;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class EventRequestStatusUpdateResult {
    private List<ParticipationRequestDto> confirmedRequests;
    private List<ParticipationRequestDto> rejectedRequests;
}
