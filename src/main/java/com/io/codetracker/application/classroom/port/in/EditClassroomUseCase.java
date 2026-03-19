package com.io.codetracker.application.classroom.port.in;

import com.io.codetracker.application.classroom.command.EditClassroomCommand;
import com.io.codetracker.application.classroom.error.EditClassroomError;
import com.io.codetracker.application.classroom.result.ClassroomData;
import com.io.codetracker.common.result.Result;

public interface EditClassroomUseCase {
    Result<ClassroomData, EditClassroomError> execute(EditClassroomCommand command);
}
