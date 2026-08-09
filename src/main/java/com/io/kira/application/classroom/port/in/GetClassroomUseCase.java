package com.io.kira.application.classroom.port.in;


import java.util.UUID;
import com.io.kira.application.classroom.error.SimpleClassroomError;
import com.io.kira.application.classroom.result.GetClassroomsProfessorData;
import com.io.kira.common.result.Result;

import java.util.List;

public interface GetClassroomUseCase {
    Result<List<GetClassroomsProfessorData>, SimpleClassroomError> execute(UUID userId);
}

