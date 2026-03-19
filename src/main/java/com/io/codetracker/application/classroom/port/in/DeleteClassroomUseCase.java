package com.io.codetracker.application.classroom.port.in;

import com.io.codetracker.application.classroom.command.DeleteClassroomCommand;
import com.io.codetracker.application.classroom.result.DeleteClassroomResult;

public interface DeleteClassroomUseCase {
    DeleteClassroomResult execute(DeleteClassroomCommand command);
}