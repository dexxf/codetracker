package com.io.codetracker.infrastructure.activity.factory;

import com.io.codetracker.common.id.IDGenerator;
import com.io.codetracker.domain.activity.entity.Activity;
import com.io.codetracker.domain.activity.factory.ActivityFactory;
import com.io.codetracker.domain.activity.valueObject.ActivityStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DefaultActivityFactory implements ActivityFactory {

    private final IDGenerator idGenerator;

    public DefaultActivityFactory(@Qualifier("activityIDGenerator") IDGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public Activity create(String classroomId, String instructorUserId, String title, String description, LocalDateTime dueDate, Integer maxScore, ActivityStatus status) {
        LocalDateTime now = LocalDateTime.now();
        return new Activity(idGenerator.generate(),classroomId, instructorUserId, title, description, dueDate, status, maxScore, now, now);
    }

}