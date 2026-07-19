package com.io.codetracker.application.classroom.port.in;


import java.util.UUID;
import com.io.codetracker.application.classroom.error.SimpleClassroomError;
import com.io.codetracker.application.classroom.result.GetClassroomsProfessorData;
import com.io.codetracker.common.result.Result;

import java.util.List;

public interface GetClassroomUseCase {
    Result<List<GetClassroomsProfessorData>, SimpleClassroomError> execute(UUID userId);
}

