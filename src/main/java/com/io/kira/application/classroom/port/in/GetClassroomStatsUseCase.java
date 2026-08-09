package com.io.kira.application.classroom.port.in;

import com.io.kira.application.classroom.command.ClassroomStatsCommand;
import com.io.kira.application.classroom.error.SimpleClassroomError;
import com.io.kira.application.classroom.result.ClassroomStats;
import com.io.kira.common.result.Result;

public interface GetClassroomStatsUseCase {
    Result<ClassroomStats, SimpleClassroomError> execute(ClassroomStatsCommand command);
}
