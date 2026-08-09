package com.io.kira.application.classroom.port.in;

import com.io.kira.application.classroom.command.CloseClassroomCommand;
import com.io.kira.application.classroom.error.CloseClassroomError;
import com.io.kira.application.classroom.result.ClassroomData;
import com.io.kira.common.result.Result;

public interface CloseClassroomUseCase {
    Result<ClassroomData, CloseClassroomError> execute(CloseClassroomCommand command);
}
