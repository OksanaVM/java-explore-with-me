package ru.practicum.stats.service;

import ru.practicum.dto.HitDto;
import ru.practicum.dto.ViewStatDto;

import java.time.LocalDateTime;
import java.util.List;

public interface HitService {
    HitDto addStatistic(HitDto hitDto);

    List<ViewStatDto> getStatistic(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

}
