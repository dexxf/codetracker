package com.io.codetracker.domain.classroom.service;

import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.classroom.entity.Classroom;
import com.io.codetracker.domain.classroom.repository.ClassroomDomainRepository;
import com.io.codetracker.domain.classroom.result.EditClassroomResult;

public final class UpdateClassroomService {

    private static final int MAX_NAME_LENGTH = 100;
    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_DESCRIPTION_LENGTH = 500;

    private final ClassroomDomainRepository classroomDomainRepository;

    public UpdateClassroomService(ClassroomDomainRepository classroomDomainRepository) {
        this.classroomDomainRepository = classroomDomainRepository;
    }

    public Result<Classroom, EditClassroomResult> updateAndValidate(String classroomId, String name, String description) {
        Classroom classroom = classroomDomainRepository.findByClassroomId(classroomId).orElse(null);

        if (classroom == null) {
            return Result.fail(EditClassroomResult.CLASSROOM_NOT_FOUND);
        }

        if (name == null || name.isBlank() || name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            return Result.fail(EditClassroomResult.INVALID_NAME);
        }

        if (description != null && description.length() > MAX_DESCRIPTION_LENGTH) {
            return Result.fail(EditClassroomResult.INVALID_DESCRIPTION);
        }

        classroom.updateName(name);
        classroom.updateDescription(description);

        return Result.ok(classroom);
    }
}
