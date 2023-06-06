package ru.practicum.ewm.compilations.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.service.CompilationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PublicCompilationsController {

    private final CompilationService compilationService;

    @GetMapping("/compilations")
    public List<CompilationDto> getCompilations(@RequestParam(value = "pinned", required = false) Boolean pinned,
                                                @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
                                                @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return compilationService.getCompilations(pinned, from, size);
    }

    @GetMapping("/compilations/{compId}")
    public CompilationDto getCompilationById(@PathVariable("compId") Long compId) {
        return compilationService.getCompilationById(compId);
    }
}
