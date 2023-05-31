package ru.practicum.ewm.service;


import ru.practicum.ewm.dto.CreatedHitDto;
import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.dto.ViewStatDto;

import java.time.LocalDateTime;
import java.util.List;

public interface HitService {
    CreatedHitDto createdHitDto(HitDto hitDto);

    List<ViewStatDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}

