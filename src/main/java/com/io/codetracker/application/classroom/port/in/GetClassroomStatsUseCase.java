package com.io.codetracker.application.classroom.port.in;

import com.io.codetracker.application.classroom.command.ClassroomStatsCommand;
import com.io.codetracker.application.classroom.error.SimpleClassroomError;
import com.io.codetracker.application.classroom.result.ClassroomStats;
import com.io.codetracker.common.result.Result;

public interface GetClassroomStatsUseCase {
    Result<ClassroomStats, SimpleClassroomError> execute(ClassroomStatsCommand command);
}
