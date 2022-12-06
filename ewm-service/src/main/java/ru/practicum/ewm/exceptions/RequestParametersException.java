package ru.practicum.ewm.exceptions;

public class RequestParametersException extends RuntimeException {
    public RequestParametersException(final String message) {
        super(message);
    }
}