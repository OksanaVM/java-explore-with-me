package ru.practicum.request.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.request.dto.ParticipationRequestDto;

import static ru.practicum.utility.UtilityClass.formatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestMapper {

    public static ParticipationRequestDto toRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .event(request.getEventId())
                .created(request.getCreated().format(formatter))
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .build();
    }
}
