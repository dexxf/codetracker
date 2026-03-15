package com.io.codetracker.application.classroom.result;

import com.io.codetracker.domain.classroom.result.ClassroomCreationEntity;

public record CreateClassroomData(
    String classroomId,
    String name,
    String description,
    String classCode,
    String status,
    int maxStudents,
    boolean requireApproval
) {

    public static CreateClassroomData from(ClassroomCreationEntity entities) {
        return new CreateClassroomData(
            entities.classroom().getClassroomId(),
            entities.classroom().getName(),
            entities.classroom().getDescription(),
            entities.classroom().getClassCode(),
            entities.classroom().getStatus().name(),
            entities.settings().getMaxStudents(),
            entities.settings().isRequireApproval()
        );
    }

    
}
