package com.io.kira.application.classroom.port.in;

import com.io.kira.application.classroom.command.EditClassroomCommand;
import com.io.kira.application.classroom.error.EditClassroomError;
import com.io.kira.application.classroom.result.ClassroomData;
import com.io.kira.common.result.Result;

public interface EditClassroomUseCase {
    Result<ClassroomData, EditClassroomError> execute(EditClassroomCommand command);
}
