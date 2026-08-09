package com.io.kira.domain.activity.factory;


import java.util.UUID;
import com.io.kira.domain.activity.entity.Activity;
import com.io.kira.domain.activity.valueObject.ActivityStatus;

import java.time.Instant;

public interface ActivityFactory {
    Activity create(UUID classroomId, UUID instructorUserId, String title, String description, Instant dueDate, Integer maxScore, ActivityStatus status);
}

