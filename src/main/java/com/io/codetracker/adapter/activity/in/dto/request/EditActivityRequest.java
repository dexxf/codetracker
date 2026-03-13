package com.io.codetracker.adapter.activity.in.dto.request;

import com.io.codetracker.domain.activity.valueObject.ActivityStatus;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record EditActivityRequest(@NotBlank String title, String description, LocalDateTime dueDate,
                                   Integer maxScore, @NotNull ActivityStatus status) {}