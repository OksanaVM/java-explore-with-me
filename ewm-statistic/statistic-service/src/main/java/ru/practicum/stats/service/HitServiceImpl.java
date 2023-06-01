package ru.practicum.stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats.dto.HitDto;
import ru.practicum.stats.dto.OutputHitDto;
import ru.practicum.stats.dto.ViewStatDto;
import ru.practicum.stats.mapper.HitMapper;
import ru.practicum.stats.mapper.ViewStatMapper;
import ru.practicum.stats.model.Hit;
import ru.practicum.stats.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {

    private final HitRepository hitRepository;

    @Override
    @Transactional
    public OutputHitDto creatHit(HitDto hitDto) {
        Hit hit = HitMapper.toHit(hitDto);

        return HitMapper
                .toCreatedHitDto(hitRepository.save(hit));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViewStatDto> getStats(LocalDateTime start, LocalDateTime end,
                                      List<String> uris, Boolean unique) {
        if (uris.isEmpty()) {
            return ViewStatMapper
                    .toViewStatDto(hitRepository.getViewStats(start, end));
        }

        return ViewStatMapper
                .toViewStatDto(hitRepository.getViewStats(uris, start, end, unique));
    }
}

