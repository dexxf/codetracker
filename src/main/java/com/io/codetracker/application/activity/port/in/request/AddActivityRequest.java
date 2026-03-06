package com.io.codetracker.application.activity.port.in.request;

import com.io.codetracker.domain.activity.valueObject.ActivityStatus;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record AddActivityRequest(@NotBlank String title, String description, @FutureOrPresent LocalDateTime dueDate,
                                 @Min(0) @Max(1000) Integer maxScore, @NotNull ActivityStatus status) {}