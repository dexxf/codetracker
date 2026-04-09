package com.io.codetracker.application.activity.result;

public record SubmittedActivityStudentData(
        String userId,
        String firstName,
        String lastName,
        String profileUrl
) {
}
