package ru.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.service.HitService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.mapper.MapperDto.pattern;


@RequiredArgsConstructor
@RestController
@Slf4j
public class ServiceController {

    private final HitService hitService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHitDto create(@RequestBody EndpointHitDto endpointHitDto) {
        log.info("Сохранение информации о запросе пользователя");
        return hitService.createHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> getStats(
            @RequestParam(name = "start", required = false) @DateTimeFormat(pattern = pattern) LocalDateTime start,
            @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = pattern) LocalDateTime end,
            @RequestParam(name = "uris", required = false) List<String> uris,
            @RequestParam(name = "unique", defaultValue = "false") Boolean unique)  {
        log.info("Получение статистики по посещениям с {} по {}, uris={}, unique={}", start, end, uris, unique);
        return hitService.getStats(start, end, uris, unique);
    }
}

