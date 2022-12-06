package ru.practicum.ewm.exceptions;


public class NotFoundException extends RuntimeException {
    public NotFoundException(String objectName, Long id) {
        super(String.format("%s with id=%d was not found.", objectName, id));
    }
}