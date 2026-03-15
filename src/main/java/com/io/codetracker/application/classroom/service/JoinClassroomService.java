package com.io.codetracker.application.classroom.service;

import com.io.codetracker.application.classroom.command.JoinClassroomCommand;
import com.io.codetracker.application.classroom.error.ClassroomJoinError;
import com.io.codetracker.application.classroom.port.in.JoinClassroomUseCase;
import com.io.codetracker.application.classroom.port.out.ClassroomStudentAppRepository;
import com.io.codetracker.application.classroom.result.ClassroomJoinResult;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.classroom.entity.ClassroomStudent;
import com.io.codetracker.domain.classroom.result.ClassroomJoinFailResult;
import com.io.codetracker.domain.classroom.result.ClassroomJoinValidationResult;
import com.io.codetracker.domain.classroom.result.ClassroomStudentCreationResult;
import com.io.codetracker.domain.classroom.service.ClassroomJoinService;
import com.io.codetracker.domain.classroom.service.ClassroomStudentCreationService;
import com.io.codetracker.domain.classroom.valueObject.StudentStatus;
import org.springframework.stereotype.Service;

@Service
public final class JoinClassroomService implements JoinClassroomUseCase {

    private final ClassroomJoinService joinService;
    private final ClassroomStudentCreationService creationService;
    private final ClassroomStudentAppRepository studentRepository;

    public JoinClassroomService(ClassroomJoinService joinService, ClassroomStudentCreationService creationService,
                                ClassroomStudentAppRepository studentRepository) {
        this.joinService = joinService;
        this.creationService = creationService;
        this.studentRepository = studentRepository;
    }

    public Result<ClassroomJoinResult, ClassroomJoinError> execute(JoinClassroomCommand command) {
        Result<ClassroomJoinValidationResult, ClassroomJoinFailResult> validation = joinService.validate(command.userId(), command.code(), command.passcode());

        if (!validation.success()) {
            return Result.fail(ClassroomJoinError.from(validation.error()));
        }

        ClassroomJoinValidationResult joinResult = validation.data();
        Result<ClassroomStudent, ClassroomStudentCreationResult> creation = creationService.createClassroomStudent(
                joinResult.classroom().getClassroomId(),
                command.userId(),
                StudentStatus.ACTIVE
        );

        if (!creation.success()) {
            return Result.fail(ClassroomJoinError.from(creation.error()));
        }

        ClassroomStudent student = creation.data();

        studentRepository.save(student);

        return Result.ok(ClassroomJoinResult.from(student));
    }
}