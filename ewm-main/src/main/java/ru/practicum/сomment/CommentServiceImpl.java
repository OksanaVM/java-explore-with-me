package ru.practicum.сomment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventsRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.State;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.сomment.dto.CommentDto;
import ru.practicum.сomment.dto.NewCommentDto;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.сomment.MapperComment.toComment;
import static ru.practicum.сomment.MapperComment.toCommentDto;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventsRepository eventsRepository;

    /**
     * Добавление нового комментария к опубликованному событию
     */
    @Transactional
    public CommentDto createComment(Long userId, Long eventId, NewCommentDto commentDto) {
        User user = checkUser(userId);
        Event event = findEventById(eventId);
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Нельзя добавить комментарий. Событие еще не опубликовано");
        }
        EventComment eventComment = toComment(commentDto, event, user);
        EventComment newEventComment = commentRepository.save(eventComment);
        return toCommentDto(newEventComment);
    }

    /**
     * Получить полную информацию комментария по его id
     */
    public CommentDto getComment(Long idComment) {
        EventComment eventComment = commentRepository.findById(idComment)
                .orElseThrow(() -> new NotFoundException("Комментария с таким id  не найдено"));
        return toCommentDto(eventComment);
    }

    /**
     * Удаление комментария его автором
     */
    @Transactional
    public void deleteComment(Long userId, Long eventId, Long id) {
        User user = checkUserAndEvent(userId, eventId);
        EventComment eventComment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Комментария с таким id  не найдено"));
        if (!eventComment.getAuthor().getId().equals(user.getId())) {
            throw new ConflictException("Удалить комментарий может только его автор");
        }
        commentRepository.deleteById(id);
    }

    /**
     * Редактирование комментария его автором
     */
    @Transactional
    public CommentDto updateComment(Long userId, Long eventId, Long commentId, NewCommentDto commentDto) {
        User user = checkUserAndEvent(userId, eventId);
        EventComment eventComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментария с таким id  не найдено"));
        if (!eventComment.getAuthor().getId().equals(user.getId())) {
            throw new ConflictException("Редактировать комментарий может только его автор");
        }
        eventComment.setText(commentDto.getText());
        EventComment updatedEventComment = commentRepository.save(eventComment);
        return toCommentDto(updatedEventComment);
    }

    /**
     * Получить список комментариев по id события
     */
    public List<CommentDto> getCommentForEvent(Long idEvent, Integer from, Integer size) {
        Event event = findEventById(idEvent);
        PageRequest page = PageRequest.of(from, size);
        List<EventComment> eventComments = commentRepository.findByEvent(event, page);
        return eventComments.stream()
                .map(MapperComment::toCommentDto)
                .collect(Collectors.toList());
    }

    /**
     * Получить список комментариев по одному или нескольким фильтрам: текст комментария, id события, id авторо.
     */
    public List<CommentDto> getCommentsByFilters(String text, Long idEvent, Long idUser, Integer from, Integer size) {
        User user = null;
        Event event = null;
        PageRequest page = PageRequest.of(from, size);
        if (idUser != null) {
            user = checkUser(idUser);
        }
        if (idEvent != null) {
            event = findEventById(idEvent);
        }
        List<EventComment> eventComments = commentRepository.getCommentsByFilters(text, user, event, page);
        return eventComments.stream()
                .map(MapperComment::toCommentDto)
                .collect(Collectors.toList());
    }

    /**
     * Удаление комментария администратором
     */
    @Transactional
    public void deleteCommentByAdmin(Long id) {
        commentRepository.deleteById(id);
    }

    /**
     * Редактирование комментария администратором
     */
    @Transactional
    public CommentDto updateCommentByAdmin(Long commentId, NewCommentDto commentDto) {
        EventComment eventComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментария с таким id  не найдено"));
        eventComment.setText(commentDto.getText());
        EventComment updatedEventComment = commentRepository.save(eventComment);
        return toCommentDto(updatedEventComment);
    }

    private User checkUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id  не найден"));
    }

    private Event findEventById(Long eventId) {
        return eventsRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с таким id не найдено"));
    }

    private User checkUserAndEvent(Long userId, Long eventId) {
        findEventById(eventId);
        return checkUser(userId);
    }

}
