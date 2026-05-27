package com.io.codetracker.application.classroom.result;

import java.time.Instant;

public record ClassroomActivityCreatedData(
        String activityId,
        String title,
        Instant createdAt
) {
}
