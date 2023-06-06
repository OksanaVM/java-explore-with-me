package ru.practicum.evm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.evm.dto.HitDto;
import ru.practicum.evm.dto.OutputHitDto;
import ru.practicum.evm.dto.ViewStatDto;
import ru.practicum.evm.service.HitService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ServiceController {

    private final HitService hitService;

    @PostMapping("/hit")
    public OutputHitDto create(@RequestBody @Valid HitDto hitDto) {
        log.info("Сохранение информации о запросе пользователя");
        return hitService.createHit(hitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatDto> getStats(@RequestParam("start") LocalDateTime start,
                                      @RequestParam("end") LocalDateTime end,
                                      @RequestParam(value = "uris", required = false, defaultValue = "") List<String> uris,
                                      @RequestParam(value = "unique", required = false, defaultValue = "false") Boolean unique) {
        log.info("Получение статистики по посещениям с {} по {}, uris={}, unique={}", start, end, uris, unique);
        return hitService.getStats(start, end, uris, unique);
    }
}

