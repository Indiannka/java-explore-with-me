package ru.practicum.ewm.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ErrorResponse {

    @JsonIgnore
    private List<StackTraceElement> details;
    private String message;
    private String reason;
    private String status;
    private String timestamp;

    public ErrorResponse(List<StackTraceElement> details, String message, String reason, String status, String timestamp) {
        this.details = details;
        this.message = message;
        this.reason = reason;
        this.status = status;
        this.timestamp = timestamp;
    }
}
