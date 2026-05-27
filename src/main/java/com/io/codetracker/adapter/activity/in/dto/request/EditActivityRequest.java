package com.io.codetracker.adapter.activity.in.dto.request;

import com.io.codetracker.domain.activity.valueObject.ActivityStatus;
import jakarta.validation.constraints.*;

import java.time.Instant;

public record EditActivityRequest(@NotBlank String title, String description, Instant dueDate,
                                   Integer maxScore, @NotNull ActivityStatus status) {}
