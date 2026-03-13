package com.io.codetracker.application.classroom.port.in;

import com.io.codetracker.application.classroom.result.GetClassroomsProfessorData;
import com.io.codetracker.common.result.Result;

import java.util.List;

public interface GetClassroomUseCase {
    Result<List<GetClassroomsProfessorData>, String> execute(String userId);
}
