package com.io.codetracker.application.activity.result;


import java.util.UUID;
public record StudentActivityInfoStudentData(
        UUID userId,
        String firstName,
        String lastName,
        String profileUrl
) {
}

