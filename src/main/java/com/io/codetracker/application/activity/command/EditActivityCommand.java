package com.io.codetracker.application.activity.command;


import java.util.UUID;
import com.io.codetracker.domain.activity.valueObject.ActivityStatus;

import java.time.Instant;

public record EditActivityCommand(UUID userId,UUID classroomId,String activityId, String title, String description, Instant dueDate, ActivityStatus status,
                                  Integer maxScore) {
}

