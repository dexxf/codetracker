package com.io.kira.adapter.activity.out.persistence.mapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.io.kira.domain.activity.entity.Activity;
import com.io.kira.domain.activity.valueObject.ActivityStatus;

import java.time.Instant;
import java.util.UUID;

public abstract class ActivityJacksonMixIn {

    @JsonCreator
    static Activity reconstitute(
            String activityId,
            UUID classroomId,
            UUID instructorUserId,
            String title,
            String description,
            Instant dueDate,
            ActivityStatus status,
            Integer maxScore,
            Instant createdAt,
            Instant updatedAt) {
        throw new UnsupportedOperationException();
    }



}