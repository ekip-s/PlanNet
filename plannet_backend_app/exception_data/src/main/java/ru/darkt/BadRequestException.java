package ru.darkt;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class BadRequestException extends RuntimeException {

    private final String reason;
    private final LocalDateTime timestamp;

    public BadRequestException(String message, String reason) {
        super(message);
        this.reason = reason;
        this.timestamp = LocalDateTime.now();
    }
}