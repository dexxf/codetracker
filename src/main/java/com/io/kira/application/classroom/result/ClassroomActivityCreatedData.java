package com.io.kira.application.classroom.result;

import java.time.Instant;
import java.util.UUID;

public record ClassroomActivityCreatedData(
        UUID activityId,
        String title,
        Instant createdAt
) {
}
