package ru.practicum.ewm.requests.dto;

import lombok.Value;
import ru.practicum.ewm.events.dto.State;

import javax.validation.constraints.NotNull;
import java.util.List;

@Value
public class NewRequestUpdateDto {
    @NotNull
    List<Long> requestIds;
    @NotNull
    State status;
}
