package ru.practicum.ewm.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.location.model.Location;

import java.time.LocalDateTime;

import static ru.practicum.ewm.UtilityClass.pattern;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEvent {
    @Length(min = 20, max = 2000)
    private String annotation;
    private Long category;
    @Length(min = 20, max = 7000)
    private String description;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = pattern)
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private State stateAction;
    @Length(min = 3, max = 120)
    private String title;
}