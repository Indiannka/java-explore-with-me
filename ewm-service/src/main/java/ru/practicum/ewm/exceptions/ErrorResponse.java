package ru.practicum.ewm.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ErrorResponse {

    @JsonIgnore
    private List<StackTraceElement> details;
    private String message;
    private String reason;
    private String status;
    private String timestamp;
}