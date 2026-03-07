package com.io.codetracker.application.classroom.command;

import com.io.codetracker.domain.classroom.valueObject.StudentStatus;

public record GetClassroomStudentCommand(
        String userId,
        String classroomId,
        StudentStatus status,        // used on filtering what status of student to show.
        boolean ascending)           // true = ascending, false = descending.
        {

}