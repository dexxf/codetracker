package com.io.kira.application.classroom.port.in;

import com.io.kira.application.classroom.command.GetClassroomStudentCommand;
import com.io.kira.application.classroom.error.SimpleClassroomError;
import com.io.kira.application.classroom.result.ClassroomStudentData;
import com.io.kira.common.result.Result;

import java.util.List;

public interface GetClassroomStudentUseCase {
    Result<List<ClassroomStudentData>, SimpleClassroomError> execute(GetClassroomStudentCommand command);
}
