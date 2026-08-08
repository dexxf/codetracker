package com.io.codetracker.adapter.classroom.out.persistence.mapper;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.io.codetracker.domain.classroom.entity.ClassroomStudent;
import com.io.codetracker.domain.classroom.valueObject.StudentStatus;

public abstract class ClassroomStudentJacksonMixIn {

    @JsonCreator
    static ClassroomStudent reconstitute(
            UUID classroomId,
             UUID studentUserId,
             StudentStatus status,
             Instant lastActiveAt,
             Instant joinedAt,
             Instant leftAt) {
        return ClassroomStudent.reconstitute(classroomId, studentUserId, status, lastActiveAt, joinedAt, leftAt);
    }
}