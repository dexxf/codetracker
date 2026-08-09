package com.io.kira.adapter.activity.in.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SubmitExistingRepositoryRequest(@NotBlank String repositoryUrl) {
}
