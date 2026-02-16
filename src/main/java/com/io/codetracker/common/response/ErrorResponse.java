package com.io.codetracker.common.response;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public record ErrorResponse(
    String message,
    OffsetDateTime timestamp,
    int status
) {
    public static ErrorResponse of(String message, int status) {
        return new ErrorResponse(message, OffsetDateTime.now(ZoneOffset.UTC), status);
    }
}