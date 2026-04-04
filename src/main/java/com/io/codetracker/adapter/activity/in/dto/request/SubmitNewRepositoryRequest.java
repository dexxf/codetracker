package com.io.codetracker.adapter.activity.in.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SubmitNewRepositoryRequest(@NotBlank String repositoryName) {
}
