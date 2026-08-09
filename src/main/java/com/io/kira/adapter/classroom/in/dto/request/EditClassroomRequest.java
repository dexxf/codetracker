package com.io.kira.adapter.classroom.in.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditClassroomRequest(
    @NotBlank String name,
    String description,
    @NotNull Integer maxStudents
) {
}
