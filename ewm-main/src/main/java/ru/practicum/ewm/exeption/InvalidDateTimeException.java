package ru.practicum.ewm.exeption;

public class InvalidDateTimeException extends RuntimeException {
    public InvalidDateTimeException(final String message) {
        super(message);
    }
}
