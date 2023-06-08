package ru.practicum.ewm.requests.dto;

import lombok.Builder;
import lombok.Getter;
import ru.practicum.ewm.events.dto.State;

@Getter
@Builder
public class ParticipationRequestDto {
    private Long id;
    private Long event;
    private String created;
    private Long requester;
    private State status;
}