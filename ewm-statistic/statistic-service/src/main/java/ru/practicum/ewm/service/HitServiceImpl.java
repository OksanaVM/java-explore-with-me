package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.CreatedHitDto;
import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.dto.ViewStatDto;
import ru.practicum.ewm.mapper.HitMapper;
import ru.practicum.ewm.mapper.ViewStatMapper;
import ru.practicum.ewm.model.Hit;
import ru.practicum.ewm.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {

    private final HitRepository hitRepository;

    @Override
    @Transactional
    public CreatedHitDto createdHitDto(HitDto hitDto) {
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

