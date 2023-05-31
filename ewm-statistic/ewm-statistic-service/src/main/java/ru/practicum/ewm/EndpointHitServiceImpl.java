package ru.practicum.ewm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.CreatedEndpointHitDto;
import ru.practicum.ewm.dto.EndpointHitDto;

import ru.practicum.ewm.dto.ViewStatDto;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EndpointHitServiceImpl implements EndpointHitService {

    private final EndpointHitRepository endpointHitRepository;

    @Override
    @Transactional
    public CreatedEndpointHitDto createdEndpointHitDto(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = EndpointHitMapper.toEndpointHit(endpointHitDto);

        return EndpointHitMapper
                .toCreatedEndpointHitDto(endpointHitRepository.save(endpointHit));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViewStatDto> getStats(LocalDateTime start, LocalDateTime end,
                                      List<String> uris, Boolean unique) {
        if (uris.isEmpty()) {
            return ViewStatMapper
                    .toViewStatDto(endpointHitRepository.getViewStats(start, end));
        }

        return ViewStatMapper
                .toViewStatDto(endpointHitRepository.getViewStats(uris, start, end, unique));
    }
}

