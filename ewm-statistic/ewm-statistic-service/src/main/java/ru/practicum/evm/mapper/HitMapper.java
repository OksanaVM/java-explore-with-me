package ru.practicum.evm.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.evm.dto.EndpointHitDto;
import ru.practicum.evm.dto.HitDto;
import ru.practicum.evm.dto.OutputHitDto;
import ru.practicum.evm.model.EndpointHit;

import java.time.LocalDateTime;

import static ru.practicum.evm.mapper.ViewStatMapper.formatter;

@UtilityClass
public class HitMapper {

    public static OutputHitDto toCreatedHitDto(EndpointHit endpointHit) {
        return new OutputHitDto(
                endpointHit.getId(),
                endpointHit.getApp(),
                endpointHit.getUri(),
                endpointHit.getIp(),
                endpointHit.getTimestamp());
    }

    public static EndpointHit toHit(HitDto hitDto) {
        EndpointHit endpointHit = new EndpointHit();
        endpointHit.setApp(hitDto.getApp());
        endpointHit.setUri(hitDto.getUri());
        endpointHit.setIp(hitDto.getIp());
        endpointHit.setTimestamp(hitDto.getTimestamp());
        return endpointHit;
    }

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
