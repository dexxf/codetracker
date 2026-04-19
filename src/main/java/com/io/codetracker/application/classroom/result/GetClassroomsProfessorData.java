package com.io.codetracker.application.classroom.result;

import com.io.codetracker.domain.classroom.entity.Classroom;

public record GetClassroomsProfessorData (String classroomId,String className, String classCode, String description,
                                          String instructorId, Long studentCount, String status, Integer maxStudent) {

    public static GetClassroomsProfessorData from(Classroom classroom, Long studentCount, Integer maxStudent) {
        return new GetClassroomsProfessorData(
                classroom.getClassroomId(),
                classroom.getName(),
                classroom.getClassCode(),
                classroom.getDescription(),
                classroom.getInstructorUserId(),
                studentCount,
                classroom.getStatus().name(),
                maxStudent
        );
    }

}