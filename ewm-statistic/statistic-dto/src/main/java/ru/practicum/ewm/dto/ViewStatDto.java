package ru.practicum.ewm.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Value
public class ViewStatDto implements Serializable {
    @NotBlank
    String app;
    @NotBlank
    String uri;
    @NotNull
    Long hits;
}
