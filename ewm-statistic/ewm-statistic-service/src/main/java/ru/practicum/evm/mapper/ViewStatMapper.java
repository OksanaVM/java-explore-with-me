package ru.practicum.evm.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.evm.dto.ViewStatDto;
import ru.practicum.evm.model.ViewStat;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ViewStatMapper {
    public static final String pattern = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
//    public static List<ViewStatDto> toViewStatDto(List<ViewStat> viewStats) {
//        return viewStats
//                .stream()
//                .map(viewStat -> new ViewStatDto(viewStat.getApp(), viewStat.getUri(), viewStat.getHits()))
//                .collect(Collectors.toList());
//    }

    public static ViewStatDto toViewStatsDto(ViewStat viewStats) {
        return ViewStatDto.builder()
                .app(viewStats.getApp())
                .uri(viewStats.getUri())
                .hits(viewStats.getHits())
                .build();
    }
}
