package com.io.codetracker.application.classroom.port.in;

import com.io.codetracker.application.classroom.command.GetClassroomStudentCommand;
import com.io.codetracker.application.classroom.error.SimpleClassroomError;
import com.io.codetracker.application.classroom.result.ClassroomStudentData;
import com.io.codetracker.common.result.Result;

import java.util.List;

public interface GetClassroomStudentUseCase {
    Result<List<ClassroomStudentData>, SimpleClassroomError> execute(GetClassroomStudentCommand command);
}
