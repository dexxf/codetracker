package com.io.codetracker.application.classroom.port.in;

import com.io.codetracker.application.classroom.command.CreateClassroomCommand;
import com.io.codetracker.application.classroom.error.CreateClassroomError;
import com.io.codetracker.application.classroom.result.CreateClassroomData;
import com.io.codetracker.common.result.Result;

public interface CreateClassroomUseCase {
    Result<CreateClassroomData, CreateClassroomError> execute(String userId, CreateClassroomCommand command);
}