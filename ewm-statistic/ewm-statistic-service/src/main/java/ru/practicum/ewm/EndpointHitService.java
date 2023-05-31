package ru.practicum.ewm;


import ru.practicum.ewm.dto.CreatedEndpointHitDto;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.ViewStatDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitService {
    CreatedEndpointHitDto createdEndpointHitDto(EndpointHitDto endpointHitDto);

    List<ViewStatDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}

