package com.io.codetracker.application.classroom.port.in;

import com.io.codetracker.application.classroom.result.GetJoinClassroomDataResult;

import java.util.List;

public interface GetJoinClassroomUseCase {
    List<GetJoinClassroomDataResult> execute(String userId);
}
