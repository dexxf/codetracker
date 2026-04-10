package com.io.codetracker.application.classroom.result;

import java.time.LocalDateTime;

public record ClassroomActivityCreatedData(
        String activityId,
        String activityTitle,
        LocalDateTime createdAt
) {
}
