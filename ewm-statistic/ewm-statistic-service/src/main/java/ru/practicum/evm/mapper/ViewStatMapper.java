package ru.practicum.evm.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.evm.dto.ViewStatDto;
import ru.practicum.evm.model.ViewStat;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ViewStatMapper {

    public static List<ViewStatDto> toViewStatDto(List<ViewStat> viewStats) {
        return viewStats
                .stream()
                .map(viewStat -> new ViewStatDto(viewStat.getApp(), viewStat.getUri(), viewStat.getHits()))
                .collect(Collectors.toList());
    }
}
