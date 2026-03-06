package com.io.codetracker.application.classroom.result;

import com.io.codetracker.domain.classroom.entity.Classroom;

public record GetClassroomsProfessorData (String classroomId,String className, String classCode, String description,
                                          String instructorId, int studentCount) {

    public static GetClassroomsProfessorData from(Classroom classroom, int studentCount) {
        return new GetClassroomsProfessorData(
                classroom.getClassroomId(),
                classroom.getName(),
                classroom.getClassCode(),
                classroom.getDescription(),
                classroom.getInstructorUserId(),
                studentCount
        );
    }

}