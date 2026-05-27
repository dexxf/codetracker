package com.io.codetracker.application.activity.port.in;

import com.io.codetracker.application.activity.command.GetActivityCommand;
import com.io.codetracker.application.activity.error.GetClassroomStudentActivityError;
import com.io.codetracker.application.activity.result.StudentActivityViewData;
import com.io.codetracker.common.result.Result;

import java.util.List;

public interface GetClassroomStudentActivityUseCase {
    Result<List<StudentActivityViewData>, GetClassroomStudentActivityError> getStudentClassroomActivity(GetActivityCommand command);
}
