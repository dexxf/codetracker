package com.io.codetracker.application.classroom.port.in;


import java.util.UUID;
import com.io.codetracker.application.classroom.result.GetJoinClassroomDataResult;

import java.util.List;

public interface GetJoinClassroomUseCase {
    List<GetJoinClassroomDataResult> execute(UUID userId);
}

