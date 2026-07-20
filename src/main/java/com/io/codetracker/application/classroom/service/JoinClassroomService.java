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
        Result<ClassroomJoinValidationResult, ClassroomJoinFailResult> validation =
                joinService.validate(command.userId(), command.code(), command.passcode());

        if (!validation.success()) {
            return Result.fail(ClassroomJoinError.from(validation.error()));
        }

        ClassroomJoinValidationResult joinResult = validation.data();
        UUID classroomId = joinResult.classroom().getClassroomId();
        boolean requireApproval = joinResult.classroomSettings().isRequireApproval();

        ClassroomStudent existingStudent = studentRepository
                .findByClassroomIdAndStudentUserId(classroomId, command.userId())
                .orElse(null);

        if (existingStudent != null) {
            if (existingStudent.getStatus() == StudentStatus.KICKED) {
                return Result.fail(ClassroomJoinError.USER_KICKED);
            }

            if (requireApproval) {
                existingStudent.rejoinWithApproval();
            } else {
                existingStudent.rejoinWithoutApproval();
            }

            studentRepository.save(existingStudent);
            return Result.ok(toResult(existingStudent, joinResult));
        }

        ClassroomStudent student = requireApproval
                ? ClassroomStudent.createPendingStudent(classroomId, command.userId())
                : ClassroomStudent.createActiveStudent(classroomId, command.userId());

        studentRepository.save(student);
        return Result.ok(toResult(student, joinResult));
    }

    private ClassroomJoinResult toResult(ClassroomStudent student, ClassroomJoinValidationResult joinResult) {
        String passcode = joinResult.classroomSettings().getPasscode();
        boolean hasPassword = passcode != null && !passcode.isBlank();
        boolean needsApproval = student.getStatus() == StudentStatus.PENDING;
        return ClassroomJoinResult.from(student, hasPassword, needsApproval);
    }
}