package ru.practicum.stats.mapper;

import ru.practicum.dto.ViewStatDto;
import ru.practicum.stats.model.ViewStat;

public class ViewStatMapper {
    public static ViewStatDto toViewStatsDto(ViewStat viewStat) {
        return ViewStatDto.builder()
                .app(viewStat.getApp())
                .uri(viewStat.getUri())
                .hits(viewStat.getHits())
                .build();
    }
}
