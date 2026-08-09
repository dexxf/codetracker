package com.io.kira.application.classroom.command;


import java.util.UUID;
import com.io.kira.domain.classroom.valueObject.StudentStatus;

public record GetClassroomStudentCommand(
        UUID userId,
        UUID classroomId,
        StudentStatus status,        // used on filtering what status of student to show.
        boolean ascending)           // true = ascending, false = descending.
        {

}
