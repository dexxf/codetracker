package com.io.kira.application.classroom.port.in;


import java.util.UUID;
import com.io.kira.application.classroom.result.GetJoinClassroomDataResult;

import java.util.List;

public interface GetJoinClassroomUseCase {
    List<GetJoinClassroomDataResult> execute(UUID userId);
}

