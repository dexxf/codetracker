package com.io.codetracker.application.classroom.port.in;

import com.io.codetracker.application.classroom.command.CloseClassroomCommand;
import com.io.codetracker.application.classroom.error.CloseClassroomError;
import com.io.codetracker.application.classroom.result.ClassroomData;
import com.io.codetracker.common.result.Result;

public interface CloseClassroomUseCase {
    Result<ClassroomData, CloseClassroomError> execute(CloseClassroomCommand command);
}
