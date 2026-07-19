package com.io.codetracker.infrastructure.activity.factory;


import java.util.UUID;
import com.io.codetracker.common.id.IDGenerator;
import com.io.codetracker.domain.activity.entity.Activity;
import com.io.codetracker.domain.activity.factory.ActivityFactory;
import com.io.codetracker.domain.activity.valueObject.ActivityStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DefaultActivityFactory implements ActivityFactory {

    private final IDGenerator idGenerator;

    public DefaultActivityFactory(@Qualifier("activityIDGenerator") IDGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public Activity create(UUID classroomId, UUID instructorUserId, String title, String description, Instant dueDate, Integer maxScore, ActivityStatus status) {
        Instant now = Instant.now();
        return new Activity(idGenerator.generate(),classroomId, instructorUserId, title, description, dueDate, status, maxScore, now, now);
    }

}

