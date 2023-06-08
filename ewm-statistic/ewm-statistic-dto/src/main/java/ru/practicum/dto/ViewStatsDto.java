package ru.practicum.dto;

import lombok.*;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewStatsDto {
    String app;
    String uri;
    Long hits;
}
