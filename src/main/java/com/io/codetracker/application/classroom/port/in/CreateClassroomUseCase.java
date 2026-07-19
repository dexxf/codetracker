package com.io.codetracker.application.classroom.port.in;


import java.util.UUID;
import com.io.codetracker.application.classroom.command.CreateClassroomCommand;
import com.io.codetracker.application.classroom.error.CreateClassroomError;
import com.io.codetracker.application.classroom.result.CreateClassroomData;
import com.io.codetracker.common.result.Result;

public interface CreateClassroomUseCase {
    Result<CreateClassroomData, CreateClassroomError> execute(UUID userId, CreateClassroomCommand command);
}
