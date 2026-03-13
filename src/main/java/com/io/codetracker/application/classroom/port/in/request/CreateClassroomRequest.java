package com.io.codetracker.application.classroom.port.in.request;

import jakarta.validation.constraints.*;

public record CreateClassroomRequest(@NotBlank String name,
                                     String description,
                                     @NotNull Integer maxStudents,
                                     // optional passcode. null = no passcode required.
                                     String passcode,
                                     @NotNull boolean requireApproval)
{}
