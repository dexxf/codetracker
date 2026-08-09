package com.io.kira.application.activity.result;


import java.util.UUID;
import java.util.List;

public record StudentActivitySummaryData(
        UUID userId,
        String firstName,
        String lastName,
        String profileUrl,
        List<StudentSubmissionDetailsData> studentActivities
) {
}

