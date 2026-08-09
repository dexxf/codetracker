package com.io.kira.application.classroom.port.in;

import com.io.kira.application.classroom.command.GetClassroomRecentActivitiesCommand;
import com.io.kira.application.classroom.error.GetClassroomRecentActivitiesError;
import com.io.kira.application.classroom.result.ClassroomRecentActivityData;
import com.io.kira.common.result.Result;

import java.util.List;

public interface GetClassroomRecentActivitiesUseCase {
    Result<List<ClassroomRecentActivityData>, GetClassroomRecentActivitiesError> execute(GetClassroomRecentActivitiesCommand command);
}
