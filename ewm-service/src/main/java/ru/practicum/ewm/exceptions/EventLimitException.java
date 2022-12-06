package ru.practicum.ewm.exceptions;

public class EventLimitException extends RuntimeException {
    public EventLimitException() {
        super("The participants limit has been reached. Cannot proceed request.");
    }
}
