package ru.practicum.сomment;

import ru.practicum.сomment.dto.CommentDto;
import ru.practicum.сomment.dto.NewCommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long userId, Long eventId, NewCommentDto commentDto);

    CommentDto getComment(Long idComment);

    void deleteComment(Long userId, Long eventId, Long id);

    CommentDto updateComment(Long userId, Long eventId, Long commentId, NewCommentDto commentDto);

    List<CommentDto> getCommentForEvent(Long idEvent, Integer from, Integer size);

    List<CommentDto> getCommentsByFilters(String text, Long idEvent, Long idUser, Integer from, Integer size);

    void deleteCommentByAdmin(Long id);

    CommentDto updateCommentByAdmin(Long commentId, NewCommentDto commentDto);
}
