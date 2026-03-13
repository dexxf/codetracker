package com.io.codetracker.application.classroom.port.in;

import com.io.codetracker.application.classroom.command.JoinClassroomCommand;
import com.io.codetracker.application.classroom.result.ClassroomJoinResult;
import com.io.codetracker.common.result.Result;

public interface JoinClassroomUseCase {
    Result<ClassroomJoinResult, String> execute(JoinClassroomCommand command);
}
