package ru.practicum.ewm.requests.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.events.dto.State;
import ru.practicum.ewm.events.model.Event;
import ru.practicum.ewm.events.repository.EventRepository;
import ru.practicum.ewm.exeption.BadRequestException;
import ru.practicum.ewm.exeption.ConflictException;
import ru.practicum.ewm.exeption.NotFoundException;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.requests.model.Request;
import ru.practicum.ewm.requests.repository.RequestRepository;
import ru.practicum.ewm.users.model.User;
import ru.practicum.ewm.users.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.requests.service.RequestMapper.toRequestDto;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    /**
     * Добавление запроса от текущего пользоателя на участие в событии
     */
    @Transactional
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        if (userId == null || eventId == null) {
            throw new BadRequestException("Некорректный запрос");
        }
        User user = checkUser(userId);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с таким id не найдено."));
        if (event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("Инициатор события не может добавить запрос на участие в своём событии.");
        }
        if (event.getState().equals(State.PENDING) || event.getState().equals(State.CANCELED)) {
            throw new ConflictException("Нельзя участвовать в неопубликованном событии.");
        }
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() <= event.getConfirmedRequests())
            throw new ConflictException("У события достигнут лимит запросов на участие.");
        Request request;
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            request = Request.builder()
                    .eventId(eventId)
                    .created(LocalDateTime.now())
                    .requester(user)
                    .status(State.CONFIRMED)
                    .build();
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        } else {
            request = Request.builder()
                    .eventId(eventId)
                    .created(LocalDateTime.now())
                    .requester(user)
                    .status(State.PENDING)
                    .build();
        }
        eventRepository.save(event);
        return toRequestDto(requestRepository.save(request));
    }

    /**
     * Получение информации о заявках текущего пользователя на участие в чужих событиях
     */
    public List<ParticipationRequestDto> getRequestsForUser(Long userId) {
        User user = checkUser(userId);
        List<Request> requests = requestRepository.findByRequester(user);
        return requests.stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    /**
     * Отмена своего запроса на участие в событии
     */
    @Transactional
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        checkUser(userId);
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Запроса с текущим id не найдено."));
        request.setStatus(State.CANCELED);
        return toRequestDto(requestRepository.save(request));
    }

    private User checkUser(Long idUser) {
        return userRepository.findById(idUser)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id  не найден"));
    }
}