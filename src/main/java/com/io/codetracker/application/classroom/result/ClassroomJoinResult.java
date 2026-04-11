package com.io.codetracker.application.classroom.result;

import com.io.codetracker.domain.classroom.entity.ClassroomStudent;
import com.io.codetracker.domain.classroom.valueObject.StudentStatus;

import java.time.LocalDateTime;

public record ClassroomJoinResult (String classroomId,
                                   String studentUserId,
                                   StudentStatus status,
                                   LocalDateTime joinedAt,
                                   LocalDateTime lastActiveAt,
                                   LocalDateTime leftAt,
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
