package com.io.kira.application.classroom.port.in;

import com.io.kira.application.classroom.command.JoinClassroomCommand;
import com.io.kira.application.classroom.error.ClassroomJoinError;
import com.io.kira.application.classroom.result.ClassroomJoinResult;
import com.io.kira.common.result.Result;

public interface JoinClassroomUseCase {
    Result<ClassroomJoinResult, ClassroomJoinError> execute(JoinClassroomCommand command);
}
