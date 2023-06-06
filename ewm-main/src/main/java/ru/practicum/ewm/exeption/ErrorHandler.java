package ru.practicum.ewm.exeption;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@RestControllerAdvice("ru.practicum.ewm")
public class ErrorHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(final MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        String field = Objects.requireNonNull(e.getBindingResult().getFieldError()).getField();
        Object rejectedValue = e.getBindingResult().getFieldValue(field);
        String message = String.format("Field: %s. Error: %s. Value: %s", field, errorMessage, rejectedValue);

        log.error(e.getLocalizedMessage());

        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.name(),
                "Incorrectly made request.",
                message);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingParams(MissingServletRequestParameterException e) {
        log.error(e.getLocalizedMessage());

        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.name(),
                "Incorrectly made request.",
                e.getLocalizedMessage());
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(final RuntimeException e) {
        log.error(e.getLocalizedMessage());

        return new ErrorResponse(
                HttpStatus.NOT_FOUND.name(),
                "The required object was not found.",
                e.getLocalizedMessage());
    }

    @ExceptionHandler({OperationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleOperationException(final RuntimeException e) {
        log.error(e.getLocalizedMessage());

        return new ErrorResponse(
                HttpStatus.CONFLICT.name(),
                "For the requested operation the conditions are not met.",
                e.getLocalizedMessage());
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleViolationException(final DataIntegrityViolationException e) {
        log.error(e.getLocalizedMessage());

        return new ErrorResponse(
                HttpStatus.CONFLICT.name(),
                "Integrity constraint has been violated.",
                e.getLocalizedMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Throwable e) {
        log.error(e.getMessage());

        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                e.getMessage());
    }

    @Value
    @Builder
    @AllArgsConstructor
    @Jacksonized
    public static class ErrorResponse {
        String status;
        String reason;
        String message;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp = LocalDateTime.now();
    }
}
