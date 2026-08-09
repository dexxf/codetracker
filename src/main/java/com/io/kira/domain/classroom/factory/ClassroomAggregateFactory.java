package com.io.kira.domain.classroom.factory;

import java.util.UUID;

import com.io.kira.domain.classroom.aggregate.ClassroomAggregate;

public interface ClassroomAggregateFactory {

    ClassroomAggregate create(
            UUID instructorUserId,
            String name,
            String description,
            int maxStudents,
            boolean requireApproval,
            String passcode
    );
}
