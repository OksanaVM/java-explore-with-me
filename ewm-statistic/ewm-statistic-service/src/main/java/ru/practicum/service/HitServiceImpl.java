package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.exeption.StartEndRangeException;
import ru.practicum.mapper.MapperDto;
import ru.practicum.model.EndpointHit;
import ru.practicum.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.mapper.MapperDto.toEndpointHit;
import static ru.practicum.mapper.MapperDto.toEndpointHitDto;

@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {

    private final HitRepository hitRepository;

    @Override
    @Transactional
    public EndpointHitDto createHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = toEndpointHit(endpointHitDto);
        return toEndpointHitDto(hitRepository.save(endpointHit));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end,
                                       List<String> uris, Boolean unique) {
        checkDate(start, end);
        if (uris == null) {
            if (unique) {
                return hitRepository.getStatisticsWithUniqueIp(start, end).stream()
                        .map(MapperDto::toViewStatsDto)
                        .collect(Collectors.toList());
            } else {
                return hitRepository.getAllStatistics(start, end).stream()
                        .map(MapperDto::toViewStatsDto)
                        .collect(Collectors.toList());
            }
        } else {
            if (unique) {
                return hitRepository.getStatisticsWithUniqueIpAndUris(start, end, uris).stream()
                        .map(MapperDto::toViewStatsDto)
                        .collect(Collectors.toList());
            } else {
                return hitRepository.getAllStatisticsWithUris(start, end, uris).stream()
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

