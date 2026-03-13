package com.io.codetracker.application.classroom.port.in;

import com.io.codetracker.application.classroom.command.CreateClassroomCommand;
import com.io.codetracker.application.classroom.result.CreateClassroomData;
import com.io.codetracker.common.result.Result;

public interface CreateClassroomUseCase {
    Result<CreateClassroomData, String> execute(String userId, CreateClassroomCommand command);
}