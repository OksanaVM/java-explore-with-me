package ru.practicum.controllers.publicPart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.сomment.CommentService;
import ru.practicum.сomment.dto.CommentDto;

import java.util.List;


@Slf4j
@Validated
@RestController
@RequestMapping(path = "/comments")
@RequiredArgsConstructor
public class PublicCommentController {
    private final CommentService service;

    @GetMapping("/{id}")
    public CommentDto getComment(@PathVariable Long id) {
        log.info("Get comment with id {}", id);
        return service.getComment(id);
    }

    @GetMapping("/event/{idEvent}")
    public List<CommentDto> getCommentForEvent(@PathVariable(name = "idEvent") Long idEvent,
                                               @RequestParam(required = false, defaultValue = "0") Integer from,
                                               @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Get comments for Event idEvent {}, from {}, size {}", idEvent, from, size);
        return service.getCommentForEvent(idEvent, from, size);
    }

    @GetMapping
    public List<CommentDto> getCommentsByFilters(@RequestParam(required = false, name = "text") String text,
                                                 @RequestParam(required = false, name = "idEvent") Long idEvent,
                                                 @RequestParam(required = false, name = "idUser") Long idUser,
                                                 @RequestParam(required = false, defaultValue = "0") Integer from,
                                                 @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Get comments by Filters text {}, idEvent {}, idUser {}, from {}, size {}",
                text, idEvent, idUser, from, size);
        return service.getCommentsByFilters(text, idEvent, idUser, from, size);
    }

}
