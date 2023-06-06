package ru.practicum.evm.exeption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice("ru.practicum.service")
public class ErrorHandler {


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String errorMessage = errors.stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        String field = Objects.requireNonNull(e.getBindingResult().getFieldError()).getField();
        log.error("{} {}", field, errorMessage, e);
        return new ErrorResponse(String.format("%s %s", field, errorMessage));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(Throwable throwable) {
        log.error("Unknown error", throwable);
        return new ErrorResponse("Произошла непредвиденная ошибка.");
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ErrorResponse {
        String error;
    }

}