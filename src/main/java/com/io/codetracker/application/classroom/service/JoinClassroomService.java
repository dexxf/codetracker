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
import com.io.codetracker.domain.classroom.service.ClassroomJoinService;
import com.io.codetracker.domain.classroom.valueObject.StudentStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public final class JoinClassroomService implements JoinClassroomUseCase {

    private final ClassroomJoinService joinService;
    private final ClassroomStudentAppRepository studentRepository;

    public JoinClassroomService(ClassroomJoinService joinService,
                                ClassroomStudentAppRepository studentRepository) {
        this.joinService = joinService;
        this.studentRepository = studentRepository;
    }

    public Result<ClassroomJoinResult, ClassroomJoinError> execute(JoinClassroomCommand command) {
        Result<ClassroomJoinValidationResult, ClassroomJoinFailResult> validation = joinService.validate(command.userId(), command.code(), command.passcode());

        if (!validation.success()) {
            return Result.fail(ClassroomJoinError.from(validation.error()));
        }

        ClassroomJoinValidationResult joinResult = validation.data();
        UUID classroomId = joinResult.classroom().getClassroomId();

        ClassroomStudent existingStudent = studentRepository.findByClassroomIdAndStudentUserId(classroomId, command.userId())
                .orElse(null);
        if (existingStudent != null) {
            existingStudent.rejoin();
            studentRepository.save(existingStudent);

            boolean hasPassword = joinResult.classroomSettings().getPasscode() != null
                    && !joinResult.classroomSettings().getPasscode().isBlank();
            return Result.ok(ClassroomJoinResult.from(existingStudent, hasPassword));
        }

        ClassroomStudent student = ClassroomStudent.create(classroomId,
                command.userId(),
                StudentStatus.ACTIVE);

        studentRepository.save(student);

        boolean hasPassword = joinResult.classroomSettings().getPasscode() != null
            && !joinResult.classroomSettings().getPasscode().isBlank();
        return Result.ok(ClassroomJoinResult.from(student, hasPassword));
    }
}
