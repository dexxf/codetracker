package com.io.codetracker.application.classroom.result;


import java.util.UUID;
import com.io.codetracker.domain.classroom.entity.ClassroomStudent;
import com.io.codetracker.domain.classroom.valueObject.StudentStatus;

import java.time.Instant;

public record ClassroomJoinResult (String classroomId,
                                   UUID studentUserId,
                                   StudentStatus status,
                                   Instant joinedAt,
                                   Instant lastActiveAt,
                                   Instant leftAt,
                                   boolean hasPassword){

    public static ClassroomJoinResult from(ClassroomStudent student, boolean hasPassword) {
        return new ClassroomJoinResult(
                student.getClassroomId(),
                student.getStudentUserId(),
                student.getStatus(),
                student.getJoinedAt(),
                student.getLastActiveAt(),
                student.getLeftAt(),
                hasPassword
        );
    }
}

