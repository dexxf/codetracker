package com.io.kira.application.classroom.result;

import com.io.kira.domain.classroom.aggregate.ClassroomAggregate;

import java.util.UUID;

public record CreateClassroomData(
    UUID classroomId,
    String name,
    String description,
    String classCode,
    String status,
    int maxStudents,
    boolean requireApproval
) {

    public static CreateClassroomData from(ClassroomAggregate aggregate) {
        return new CreateClassroomData(
            aggregate.classroom().getClassroomId(),
            aggregate.classroom().getName(),
            aggregate.classroom().getDescription(),
            aggregate.classroom().getClassCode(),
            aggregate.classroom().getStatus().name(),
            aggregate.settings().getMaxStudents(),
            aggregate.settings().isRequireApproval()
        );
    }
}
