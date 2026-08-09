package com.io.kira.application.classroom.result;


import java.util.UUID;

import com.io.kira.domain.classroom.aggregate.ClassroomAggregate;

public record GetClassroomsProfessorData (UUID classroomId,String className, String classCode, String description,
                                          UUID instructorId, Long studentCount, String status, Integer maxStudent) {

    public static GetClassroomsProfessorData from(ClassroomAggregate aggregate, Long studentCount) {
        return new GetClassroomsProfessorData(
                aggregate.classroom().getClassroomId(),
                aggregate.classroom().getName(),
                aggregate.classroom().getClassCode(),
                aggregate.classroom().getDescription(),
                aggregate.classroom().getInstructorUserId(),
                studentCount,
                aggregate.classroom().getStatus().name(),
                aggregate.settings().getMaxStudents()
        );
    }

}
