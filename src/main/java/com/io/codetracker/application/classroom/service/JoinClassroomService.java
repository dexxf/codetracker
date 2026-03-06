package com.io.codetracker.application.classroom.service;

import com.io.codetracker.application.classroom.command.JoinClassroomCommand;
import com.io.codetracker.application.classroom.port.out.ClassroomStudentAppRepository;
import com.io.codetracker.application.classroom.response.ClassroomJoinResponse;
import com.io.codetracker.application.classroom.result.ClassroomJoinResult;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.classroom.entity.ClassroomStudent;
import com.io.codetracker.domain.classroom.result.ClassroomJoinValidationResult;
import com.io.codetracker.domain.classroom.result.ClassroomStudentCreationResult;
import com.io.codetracker.domain.classroom.service.ClassroomJoinService;
import com.io.codetracker.domain.classroom.service.ClassroomStudentCreationService;
import com.io.codetracker.domain.classroom.valueObject.StudentStatus;
import org.springframework.stereotype.Service;

@Service
public final class JoinClassroomService {

    private final ClassroomJoinService joinService;
    private final ClassroomStudentCreationService creationService;
    private final ClassroomStudentAppRepository studentRepository;

    public JoinClassroomService(ClassroomJoinService joinService, ClassroomStudentCreationService creationService,
                                ClassroomStudentAppRepository studentRepository) {
        this.joinService = joinService;
        this.creationService = creationService;
        this.studentRepository = studentRepository;
    }

    public ClassroomJoinResponse execute(JoinClassroomCommand command) {

        Result<ClassroomJoinValidationResult, String> validation =
                joinService.validate(command.userId(), command.code(), command.passcode());

        if (!validation.success()) {
            return ClassroomJoinResponse.fail(validation.error());
        }

        ClassroomJoinValidationResult joinResult = validation.data();
        Result<ClassroomStudent, ClassroomStudentCreationResult> creation = creationService.createClassroomStudent(
                joinResult.classroom().getClassroomId(),
                command.userId(),
                StudentStatus.ACTIVE
        );

        if (!creation.success()) {
            return ClassroomJoinResponse.fail(creation.error().getMessage());
        }

        ClassroomStudent student = creation.data();

        studentRepository.save(student);

        ClassroomJoinResult resultData = ClassroomJoinResult.from(student);
        return ClassroomJoinResponse.ok(resultData);
    }
}