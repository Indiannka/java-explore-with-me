package ru.practicum.ewm.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                Arrays.asList(ex.getStackTrace()),
                ex.getLocalizedMessage(),
                "The required object was not found.",
                HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now().toString());
        log.info("ExceptionHandler: {}", ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        ErrorResponse error = new ErrorResponse(
                Arrays.asList(ex.getStackTrace()),
                ex.getLocalizedMessage(),
                "The required object was not found.",
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now().toString());
        log.info("ExceptionHandler: {}", ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                Arrays.asList(ex.getStackTrace()),
                ex.getLocalizedMessage(),
                "The required object was not found.",
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                LocalDateTime.now().toString());
        log.info("ExceptionHandler: {}", ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}