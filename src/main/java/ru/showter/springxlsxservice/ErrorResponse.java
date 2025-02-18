package ru.showter.springxlsxservice;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class ErrorResponse {
    private String message;
    private int status;
    private Instant timestamp;
}