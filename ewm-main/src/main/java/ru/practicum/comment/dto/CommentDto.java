package ru.practicum.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import static ru.practicum.utility.UtilityClass.pattern;

@Getter
@Builder
public class CommentDto {
    private Long id;
    private String authorName;
    @NotBlank
    @Length(max = 1000)
    private String text;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = pattern)
    private LocalDateTime created;
}
