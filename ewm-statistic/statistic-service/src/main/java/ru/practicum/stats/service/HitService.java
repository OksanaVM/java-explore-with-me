package ru.practicum.stats.service;

import ru.practicum.stats.dto.HitDto;
import ru.practicum.stats.dto.OutputHitDto;
import ru.practicum.stats.dto.ViewStatDto;

import java.time.LocalDateTime;
import java.util.List;

public interface HitService {
    OutputHitDto creatHit(HitDto hitDto);

    List<ViewStatDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}

