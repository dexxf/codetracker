package com.io.codetracker.adapter.classroom.in.dto;

import jakarta.validation.constraints.*;

public record CreateClassroomRequest(@NotBlank String name, String description, @NotNull Integer maxStudents, String passcode, @NotNull Boolean requireApproval) {}
