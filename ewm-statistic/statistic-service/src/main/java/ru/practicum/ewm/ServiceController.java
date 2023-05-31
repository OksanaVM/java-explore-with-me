package ru.practicum.ewm;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CreatedHitDto;
import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.dto.ViewStatDto;
import ru.practicum.ewm.service.HitService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ServiceController {

    private final HitService hitService;

    @PostMapping("/hit")
    public CreatedHitDto create(@RequestBody @Valid HitDto hitDto) {
        return hitService.createdHitDto(hitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatDto> getStats(@RequestParam("start") LocalDateTime start,
                                      @RequestParam("end") LocalDateTime end,
                                      @RequestParam(value = "uris", required = false, defaultValue = "") List<String> uris,
                                      @RequestParam(value = "unique", required = false, defaultValue = "false") Boolean unique) {
        return hitService.getStats(start, end, uris, unique);
    }
}

