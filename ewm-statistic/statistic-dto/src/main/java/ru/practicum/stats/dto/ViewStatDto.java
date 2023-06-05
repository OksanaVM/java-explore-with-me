package ru.practicum.stats.dto;

import lombok.Value;
import lombok.extern.jackson.Jacksonized;


@Value
@Jacksonized
public class ViewStatDto {
    String app;
    String uri;
    Long hits;
}
