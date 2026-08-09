package com.io.kira.application.classroom.port.in;


import java.util.UUID;
import com.io.kira.application.classroom.command.CreateClassroomCommand;
import com.io.kira.application.classroom.error.CreateClassroomError;
import com.io.kira.application.classroom.result.CreateClassroomData;
import com.io.kira.common.result.Result;

public interface CreateClassroomUseCase {
    Result<CreateClassroomData, CreateClassroomError> execute(UUID userId, CreateClassroomCommand command);
}
