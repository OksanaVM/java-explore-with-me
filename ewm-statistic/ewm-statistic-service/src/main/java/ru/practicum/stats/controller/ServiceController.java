package ru.practicum.stats.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.ViewStatDto;
import ru.practicum.stats.mapper.HitMapper;
import ru.practicum.stats.service.HitService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ServiceController {
    private final HitService hitService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public HitDto addStatistic(@RequestBody HitDto hitDto) {
        log.info("Add endpointHitDto {}", hitDto);
        return hitService.addStatistic(hitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatDto> getStatistic(
            @RequestParam(name = "start", required = false) @DateTimeFormat(pattern = HitMapper.pattern) LocalDateTime start,
            @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = HitMapper.pattern) LocalDateTime end,
            @RequestParam(name = "uris", required = false) List<String> uris,
            @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        log.info("Get statistic from start {}, end {}, uris {}, unique {}", start, end, uris, unique);
        return hitService.getStatistic(start, end, uris, unique);
    }

}
