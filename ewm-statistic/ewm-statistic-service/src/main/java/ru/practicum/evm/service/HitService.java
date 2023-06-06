package ru.practicum.evm.service;

import ru.practicum.evm.dto.HitDto;
import ru.practicum.evm.dto.OutputHitDto;
import ru.practicum.evm.dto.ViewStatDto;

import java.time.LocalDateTime;
import java.util.List;

public interface HitService {
    OutputHitDto createHit(HitDto hitDto);

    List<ViewStatDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
