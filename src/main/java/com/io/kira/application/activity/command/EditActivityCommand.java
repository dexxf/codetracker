package com.io.kira.application.activity.command;


import java.util.UUID;
import com.io.kira.domain.activity.valueObject.ActivityStatus;

import java.time.Instant;

public record EditActivityCommand(UUID userId,UUID classroomId,UUID activityId, String title, String description, Instant dueDate, ActivityStatus status,
                                  Integer maxScore) {
}

