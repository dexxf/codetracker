package com.io.codetracker.application.classroom.port.in;

import com.io.codetracker.application.classroom.command.GetClassroomRecentActivitiesCommand;
import com.io.codetracker.application.classroom.error.GetClassroomRecentActivitiesError;
import com.io.codetracker.application.classroom.result.ClassroomRecentActivityData;
import com.io.codetracker.common.result.Result;

import java.util.List;

public interface GetClassroomRecentActivitiesUseCase {
    Result<List<ClassroomRecentActivityData>, GetClassroomRecentActivitiesError> execute(GetClassroomRecentActivitiesCommand command);
}
