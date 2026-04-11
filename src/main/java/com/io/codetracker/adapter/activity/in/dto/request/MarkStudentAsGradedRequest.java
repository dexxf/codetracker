package com.io.codetracker.adapter.activity.in.dto.request;

import jakarta.validation.constraints.Min;

public record MarkStudentAsGradedRequest(String feedback, @Min(0) Integer score) {
}