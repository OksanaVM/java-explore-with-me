package ru.practicum.stats.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.stats.dto.HitDto;
import ru.practicum.stats.dto.OutputHitDto;
import ru.practicum.stats.model.Hit;

@UtilityClass
public class HitMapper {

    public static OutputHitDto toCreatedHitDto(Hit hit) {
        return new OutputHitDto(
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
