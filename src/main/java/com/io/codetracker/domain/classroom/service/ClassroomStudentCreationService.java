package com.io.codetracker.domain.classroom.service;

import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.classroom.entity.ClassroomStudent;
import com.io.codetracker.domain.classroom.factory.ClassroomStudentFactory;
import com.io.codetracker.domain.classroom.repository.ClassroomStudentDomainRepository;
import com.io.codetracker.domain.classroom.result.ClassroomStudentCreationResult;
import com.io.codetracker.domain.classroom.valueObject.StudentStatus;

public final class ClassroomStudentCreationService {

    private final ClassroomStudentFactory factory;
    private final ClassroomStudentDomainRepository repository;

    public ClassroomStudentCreationService(ClassroomStudentFactory factory, ClassroomStudentDomainRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    public Result<ClassroomStudent, ClassroomStudentCreationResult> createClassroomStudent(String classroomId, String studentUserId, StudentStatus status) {
            if (classroomId == null || classroomId.isBlank()) {
                return Result.fail(ClassroomStudentCreationResult.CLASSROOM_ID_INVALID);
            }
            if (studentUserId == null || studentUserId.isBlank()) {
                return Result.fail(ClassroomStudentCreationResult.STUDENT_USER_ID_INVALID);
            }
            if (status == null) {
                return Result.fail(ClassroomStudentCreationResult.STATUS_INVALID);
            }

            if (repository.existsByClassroomIdAndStudentUserId(classroomId, studentUserId)) {
                return Result.fail(ClassroomStudentCreationResult.USER_ALREADY_IN_CLASSROOM);
            }

            return Result.ok(factory.create(classroomId, studentUserId, status));
    }
}
