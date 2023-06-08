package ru.practicum.ewm.requests.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.events.dto.State;
import ru.practicum.ewm.events.model.Event;
import ru.practicum.ewm.events.repository.EventRepository;
import ru.practicum.ewm.exeption.ConflictException;
import ru.practicum.ewm.exeption.NotFoundException;
import ru.practicum.ewm.requests.dto.NewRequestUpdateDto;
import ru.practicum.ewm.requests.dto.RequestDto;
import ru.practicum.ewm.requests.dto.RequestUpdateDto;
import ru.practicum.ewm.requests.model.Request;
import ru.practicum.ewm.requests.repository.RequestRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;

    private static final String REQUEST_SAME_USER_ID_EXCEPTION_MESSAGE =
            "Cannot create request for own event.";
    private static final String REQUEST_STATE_EXCEPTION_MESSAGE =
            "Cannot create request for unpublished event.";
    private static final String REQUEST_LIMIT_EXCEPTION_MESSAGE =
            "Cannot create request because of participant limitation.";
    private static final String REQUEST_NOT_FOUND_EXCEPTION_MESSAGE =
            "Request with id=%s was not found";

    private static final String EVENT_NOT_FOUND_MESSAGE = "Event with id=%s was not found";

    @Override
    @Transactional
    public RequestDto create(Long userId, Long eventId) {
        Event event = findEventById(eventId);

        validateEventForRequest(userId, event);

        List<Request> requests = requestRepository.findAllByEventId(eventId);

        checkParticipantLimit(event, requests);

        return saveRequest(eventId, userId, event.getRequestModeration());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestDto> getRequestsById(Long userId) {
        return RequestMapper.toRequestDto(requestRepository.findAllByRequesterId(userId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestDto> getRequestsByUserIdAndEventId(Long userId, Long eventId) {
        return RequestMapper.toRequestDto(requestRepository.findAllByEventId(eventId));
    }

    @Override
    @Transactional
    public RequestDto cancelRequest(Long userId, Long requestId) {
        Request request = getRequestByRequesterIdAndId(userId, requestId);

        request.setStatus(State.CANCELED);

        return RequestMapper.toRequestDto(request);
    }

    private Request getRequestByRequesterIdAndId(Long userId, Long requestId) {
        Request request = requestRepository.findByRequesterIdAndId(userId, requestId);

        if (request == null) {
            throw new NotFoundException(String.format(REQUEST_NOT_FOUND_EXCEPTION_MESSAGE,
                    requestId));
        }
        return request;
    }

    @Override
    @Transactional
    public RequestUpdateDto updateRequestStatus(Long userId, Long eventId,
                                                NewRequestUpdateDto newRequestUpdateDto) {
        validateNewRequestUpdateDto(newRequestUpdateDto);

        RequestUpdateDto requestUpdateDto = new RequestUpdateDto(new ArrayList<>(),
                new ArrayList<>());
        Event event = findEventById(eventId);

        checkEventParticipantLimit(event);
        updateRequests(newRequestUpdateDto, requestUpdateDto, event);

        return requestUpdateDto;
    }

    private void validateEventForRequest(Long userId, Event event) {
        validateEventInitiator(userId, event);
        validateEventState(event);
    }

    private void validateEventInitiator(Long userId, Event event) {
        if (event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("У события достигнут лимит запросов на участие.");
        }
    }

    private void validateEventState(Event event) {
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("У события достигнут лимит запросов на участие.");
        }
    }

    private void checkParticipantLimit(Event event, List<Request> requests) {
        if (requests.size() >= event.getParticipantLimit()) {
            throw new ConflictException("У события достигнут лимит запросов на участие.");
        }
    }

    private RequestDto saveRequest(Long eventId, Long userId, Boolean isRequestedModeration) {
        Request request = RequestMapper.toRequest(eventId, userId,
                isRequestedModeration ? State.PENDING : State.CONFIRMED);

        return RequestMapper.toRequestDto(requestRepository.save(request));
    }

    private void validateNewRequestUpdateDto(NewRequestUpdateDto newRequestUpdateDto) {
        if (newRequestUpdateDto == null) {
            throw new ConflictException("У события достигнут лимит запросов на участие.");
        }
    }

    private Event findEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> {
            throw new ConflictException("У события достигнут лимит запросов на участие.");
        });
    }

    private void checkEventParticipantLimit(Event event) {
        if (event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new ConflictException("У события достигнут лимит запросов на участие.");
        }
    }

    private void updateRequests(NewRequestUpdateDto newRequestUpdateDto,
                                RequestUpdateDto requestUpdateDto, Event event) {
        List<Long> requestIds = newRequestUpdateDto.getRequestIds();

        for (Long id : requestIds) {
            if (event.getParticipantLimit().equals(0) || !event.getRequestModeration()) {
                break;
            }

            Request request = getRequestById(id);

            validateRequestStatus(newRequestUpdateDto, request);
            updateRequestStatusAndSave(newRequestUpdateDto, requestUpdateDto, event, request);
        }
    }

    private Request getRequestById(Long id) {
        return requestRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException(String.format(REQUEST_NOT_FOUND_EXCEPTION_MESSAGE, id));
        });
    }

    private void validateRequestStatus(NewRequestUpdateDto newRequestUpdateDto, Request request) {
        if (request.getStatus().equals(State.CONFIRMED) &&
                newRequestUpdateDto.getStatus().equals(State.REJECTED)) {
            throw new ConflictException("У события достигнут лимит запросов на участие.");
        }
    }

    private void updateRequestStatusAndSave(NewRequestUpdateDto newRequestUpdateDto,
                                            RequestUpdateDto requestUpdateDto,
                                            Event event, Request request) {
        if (event.getConfirmedRequests() >= event.getParticipantLimit()
                || newRequestUpdateDto.getStatus().equals(State.REJECTED)) {
            setRequestStatusAndSave(requestUpdateDto.getRejectedRequests(), request, State.REJECTED);
        } else {
            setRequestStatusAndSave(requestUpdateDto.getConfirmedRequests(),
                    request, newRequestUpdateDto.getStatus());

            incrementEventConfirmedRequests(event);
        }
    }

    private void setRequestStatusAndSave(List<RequestDto> updatedRequests,
                                         Request request, State status) {
        request.setStatus(status);

        requestRepository.save(request);

        updatedRequests.add(RequestMapper.toRequestDto(request));
    }

    private void incrementEventConfirmedRequests(Event event) {
        event.setConfirmedRequests(event.getConfirmedRequests() + 1);

        eventRepository.save(event);
    }
}