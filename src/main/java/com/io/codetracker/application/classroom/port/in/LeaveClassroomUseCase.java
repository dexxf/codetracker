package com.io.codetracker.application.classroom.port.in;

import com.io.codetracker.application.classroom.command.LeaveClassroomCommand;
import com.io.codetracker.application.classroom.result.LeaveClassroomResult;

public interface LeaveClassroomUseCase {
    LeaveClassroomResult execute(LeaveClassroomCommand command);
}
