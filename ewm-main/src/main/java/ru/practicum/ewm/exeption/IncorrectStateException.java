package ru.practicum.ewm.exeption;

public class IncorrectStateException extends RuntimeException {
    public IncorrectStateException(final String message) {
        super(message);
    }
}
