package ru.practicum.ewm.exeption;


public class ConflictException extends RuntimeException {
    public ConflictException(final String message) {
        super(message);
    }
}
