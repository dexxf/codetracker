package com.io.kira.application.classroom.port.in;

import com.io.kira.application.classroom.command.DeleteClassroomCommand;
import com.io.kira.application.classroom.result.DeleteClassroomResult;

public interface DeleteClassroomUseCase {
    DeleteClassroomResult execute(DeleteClassroomCommand command);
}