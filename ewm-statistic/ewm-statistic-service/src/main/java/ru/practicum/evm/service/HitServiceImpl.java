package ru.practicum.evm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.evm.dto.EndpointHitDto;
import ru.practicum.evm.dto.HitDto;
import ru.practicum.evm.dto.OutputHitDto;
import ru.practicum.evm.dto.ViewStatDto;
import ru.practicum.evm.exeption.StartEndRangeException;
import ru.practicum.evm.mapper.HitMapper;
import ru.practicum.evm.mapper.ViewStatMapper;
import ru.practicum.evm.model.EndpointHit;
import ru.practicum.evm.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.evm.mapper.HitMapper.toEndpointHit;
import static ru.practicum.evm.mapper.HitMapper.toEndpointHitDto;

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
    public List<ViewStatDto> getStats(LocalDateTime start, LocalDateTime end,
                                      List<String> uris, Boolean unique) {
        checkDate(start, end);
        if (uris == null) {
            if (unique) {
                return hitRepository.getStatisticsWithUniqueIp(start, end).stream()
                        .map(ViewStatMapper::toViewStatsDto)
                        .collect(Collectors.toList());
            } else {
                return hitRepository.getAllStatistics(start, end).stream()
                        .map(ViewStatMapper::toViewStatsDto)
                        .collect(Collectors.toList());
            }
        } else {
            if (unique) {
                return hitRepository.getStatisticsWithUniqueIpAndUris(start, end, uris).stream()
                        .map(ViewStatMapper::toViewStatsDto)
                        .collect(Collectors.toList());
            } else {
                return hitRepository.getAllStatisticsWithUris(start, end, uris).stream()
                        .map(ViewStatMapper::toViewStatsDto)
                        .collect(Collectors.toList());
            }
        }


//        if (uris.isEmpty()) {
//            return ViewStatMapper
//                    .toViewStatDto(hitRepository.getViewStats(start, end));
//        }
//
//        return ViewStatMapper
//                .toViewStatDto(hitRepository.getViewStats(uris, start, end, unique));
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

