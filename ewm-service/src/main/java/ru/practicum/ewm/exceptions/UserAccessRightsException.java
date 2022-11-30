package ru.practicum.ewm.exceptions;

public class UserAccessRightsException extends RuntimeException {
    public UserAccessRightsException() {
        super("Only initiator can change event parameters");
    }
}