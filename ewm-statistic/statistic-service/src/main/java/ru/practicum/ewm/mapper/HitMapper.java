package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.CreatedHitDto;
import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.model.Hit;

public class HitMapper {

    private HitMapper() {

    }

    public static CreatedHitDto toCreatedHitDto(Hit hit) {
        return new CreatedHitDto(
                hit.getId(),
                hit.getApp(),
                hit.getUri(),
                hit.getIp(),
                hit.getTimestamp());
    }

    public static Hit toHit(HitDto hitDto) {
        Hit hit = new Hit();
        hit.setApp(hitDto.getApp());
        hit.setUri(hitDto.getUri());
        hit.setIp(hitDto.getIp());
        hit.setTimestamp(hitDto.getTimestamp());
        return hit;
    }
}
