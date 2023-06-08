package ru.practicum.ewm.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import ru.practicum.ewm.categories.dto.NewCategoryDto;
import ru.practicum.ewm.users.dto.UserShortDto;

import java.time.LocalDateTime;

import static ru.practicum.ewm.UtilityClass.pattern;

@Getter
@Builder
public class EventsShortDto {
    private Long id;
    private String annotation;
    private NewCategoryDto category;
    private Long confirmedRequests;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = pattern)
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private Long views;
}
