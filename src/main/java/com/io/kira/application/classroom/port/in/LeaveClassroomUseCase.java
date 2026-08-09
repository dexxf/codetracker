package com.io.kira.application.classroom.port.in;

import com.io.kira.application.classroom.command.LeaveClassroomCommand;
import com.io.kira.application.classroom.result.LeaveClassroomResult;

public interface LeaveClassroomUseCase {
    LeaveClassroomResult execute(LeaveClassroomCommand command);
}
