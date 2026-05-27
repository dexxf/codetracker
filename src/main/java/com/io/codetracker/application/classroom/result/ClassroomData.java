package com.io.codetracker.application.classroom.result;

import com.io.codetracker.domain.classroom.entity.Classroom;
import com.io.codetracker.domain.classroom.valueObject.ClassroomStatus;

import java.time.Instant;

public record ClassroomData(
        String classroomId,
        String instructorUserId,
        String name,
        String description,
        String classCode,
        ClassroomStatus status,
        Instant createdAt,
        Instant updatedAt
) {

    public static ClassroomData from(Classroom classroom) {
        return new ClassroomData(
                classroom.getClassroomId(),
                classroom.getInstructorUserId(),
                classroom.getName(),
                classroom.getDescription(),
                classroom.getClassCode(),
                classroom.getStatus(),
                classroom.getCreatedAt(),
                classroom.getUpdatedAt()
        );
    }
}
