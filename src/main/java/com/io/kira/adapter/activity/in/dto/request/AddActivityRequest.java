package com.io.kira.adapter.activity.in.dto.request;

import com.io.kira.domain.activity.valueObject.ActivityStatus;
import jakarta.validation.constraints.*;

import java.time.Instant;

public record AddActivityRequest(@NotBlank String title, String description, Instant dueDate,
                                 @Min(0) @Max(1000) Integer maxScore, @NotNull ActivityStatus status) {}
