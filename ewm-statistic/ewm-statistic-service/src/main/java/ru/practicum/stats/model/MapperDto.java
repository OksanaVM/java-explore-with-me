package ru.practicum.stats.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.ViewStatDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapperDto {
    public static final String pattern = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

    public static Hit toEndpointHit(HitDto hitDto) {
        return Hit.builder()
                .app(hitDto.getApp())
                .uri(hitDto.getUri())
                .ip(hitDto.getIp())
                .timestamp(LocalDateTime.parse(hitDto.getTimestamp(), formatter))
                .build();
    }

    public static HitDto toEndpointHitDto(Hit hit) {
        return HitDto.builder()
                .app(hit.getApp())
                .uri(hit.getUri())
                .ip(hit.getIp())
                .timestamp(hit.getTimestamp().format(formatter))
                .build();
    }

    public static ViewStatDto toViewStatsDto(ViewStat viewStat) {
        return ViewStatDto.builder()
                .app(viewStat.getApp())
                .uri(viewStat.getUri())
                .hits(viewStat.getHits())
                .build();
    }

}
