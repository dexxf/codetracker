package com.io.codetracker.application.activity.result;


import java.util.UUID;
import java.util.List;

public record StudentActivityInfoUserData(
        UUID userId,
        String firstName,
        String lastName,
        String profileUrl,
        List<StudentActivityInfoData> studentActivities
) {
}

