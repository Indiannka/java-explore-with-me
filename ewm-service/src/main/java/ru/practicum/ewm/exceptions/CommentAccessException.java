package ru.practicum.ewm.exceptions;

public class CommentAccessException extends RuntimeException {

    public CommentAccessException() {
        super("User may change or remove only self-created comments");
    }
}