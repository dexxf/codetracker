package com.io.kira.application.classroom.result;


import java.util.UUID;
import com.io.kira.domain.classroom.entity.ClassroomStudent;
import com.io.kira.domain.classroom.valueObject.StudentStatus;

import java.time.Instant;

public record ClassroomJoinResult (UUID classroomId,
                                   UUID studentUserId,
                                   StudentStatus status,
                                   Instant joinedAt,
                                   Instant lastActiveAt,
                                   Instant leftAt,
                                   boolean hasPassword,
                                   boolean needsApproval){

    public static ClassroomJoinResult from(ClassroomStudent student, boolean hasPassword, boolean needsApproval) {
        return new ClassroomJoinResult(
                student.getClassroomId(),
                student.getStudentUserId(),
                student.getStatus(),
                student.getJoinedAt(),
                student.getLastActiveAt(),
                student.getLeftAt(),
                hasPassword,
                needsApproval
        );
    }
}

