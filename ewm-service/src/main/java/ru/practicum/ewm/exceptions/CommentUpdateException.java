package ru.practicum.ewm.exceptions;

public class CommentUpdateException extends RuntimeException {
    public CommentUpdateException() {
        super("It is not allowed to edit comments older than 1 day");
    }
}
