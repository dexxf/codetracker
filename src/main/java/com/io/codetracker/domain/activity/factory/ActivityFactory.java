package com.io.codetracker.domain.activity.factory;


import java.util.UUID;
import com.io.codetracker.domain.activity.entity.Activity;
import com.io.codetracker.domain.activity.valueObject.ActivityStatus;

import java.time.Instant;

public interface ActivityFactory {
    Activity create(String classroomId, UUID instructorUserId, String title, String description, Instant dueDate, Integer maxScore, ActivityStatus status);
}

