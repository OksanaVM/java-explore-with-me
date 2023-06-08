package ru.practicum.stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.ViewStatDto;
import ru.practicum.stats.exception.StartEndRangeException;
import ru.practicum.stats.model.Hit;
import ru.practicum.stats.model.MapperDto;
import ru.practicum.stats.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.stats.model.MapperDto.toEndpointHit;
import static ru.practicum.stats.model.MapperDto.toEndpointHitDto;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {
    private final HitRepository repository;

    @Transactional
    public HitDto addStatistic(HitDto hitDto) {
        Hit hit = toEndpointHit(hitDto);
        return toEndpointHitDto(repository.save(hit));
    }

    public List<ViewStatDto> getStatistic(LocalDateTime start, LocalDateTime end,
                                          List<String> uris, Boolean unique) {
        checkDate(start, end);
        if (uris == null) {
            if (unique) {
                return repository.getStatisticsWithUniqueIp(start, end).stream()
                        .map(MapperDto::toViewStatsDto)
                        .collect(Collectors.toList());
            } else {
                return repository.getAllStatistics(start, end).stream()
                        .map(MapperDto::toViewStatsDto)
                        .collect(Collectors.toList());
            }
        } else {
            if (unique) {
                return repository.getStatisticsWithUniqueIpAndUris(start, end, uris).stream()
                        .map(MapperDto::toViewStatsDto)
                        .collect(Collectors.toList());
            } else {
                return repository.getAllStatisticsWithUris(start, end, uris).stream()
                        .map(MapperDto::toViewStatsDto)
                        .collect(Collectors.toList());
            }
        }
    }

    private void checkDate(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            throw new StartEndRangeException("Ошибка времени начала и конца диапазона");
        }
        if (startTime.isAfter(endTime)) {
            throw new StartEndRangeException("Ошибка времени начала и конца диапазона");
        }
    }
}
