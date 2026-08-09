package com.io.kira.application.activity.port.in;

import com.io.kira.application.activity.command.GetActivityCommand;
import com.io.kira.application.activity.error.GetClassroomStudentActivityError;
import com.io.kira.application.activity.result.StudentActivityOverviewData;
import com.io.kira.common.result.Result;

import java.util.List;

public interface GetClassroomStudentActivityUseCase {
    Result<List<StudentActivityOverviewData>, GetClassroomStudentActivityError> getStudentClassroomActivity(GetActivityCommand command);
}
