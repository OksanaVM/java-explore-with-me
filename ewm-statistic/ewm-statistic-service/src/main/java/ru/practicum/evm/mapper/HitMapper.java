package ru.practicum.evm.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.evm.dto.EndpointHitDto;
import ru.practicum.evm.model.EndpointHit;

import java.time.LocalDateTime;

import static ru.practicum.evm.mapper.ViewStatMapper.formatter;

@UtilityClass
public class HitMapper {

    public static EndpointHit toEndpointHit(EndpointHitDto endpointHitDto) {
        return EndpointHit.builder()
                .app(endpointHitDto.getApp())
                .uri(endpointHitDto.getUri())
                .ip(endpointHitDto.getIp())
                .timestamp(LocalDateTime.parse(endpointHitDto.getTimestamp(), formatter))
                .build();
    }

    public static EndpointHitDto toEndpointHitDto(EndpointHit endpointHit) {
        return EndpointHitDto.builder()
                .app(endpointHit.getApp())
                .uri(endpointHit.getUri())
                .ip(endpointHit.getIp())
                .timestamp(endpointHit.getTimestamp().format(formatter))
                .build();
    }
}
