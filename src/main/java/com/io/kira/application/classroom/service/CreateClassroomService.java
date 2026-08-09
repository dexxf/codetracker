package com.io.kira.application.classroom.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.io.kira.application.classroom.command.CreateClassroomCommand;
import com.io.kira.application.classroom.error.CreateClassroomError;
import com.io.kira.application.classroom.port.in.CreateClassroomUseCase;
import com.io.kira.application.classroom.port.out.ClassroomAppRepository;
import com.io.kira.application.classroom.result.CreateClassroomData;
import com.io.kira.common.result.Result;
import com.io.kira.domain.classroom.aggregate.ClassroomAggregate;
import com.io.kira.domain.classroom.entity.ClassroomSettings;
import com.io.kira.domain.classroom.factory.ClassroomAggregateFactory;
import com.io.kira.domain.classroom.repository.ClassroomUserDomainPort;

@Service
public class CreateClassroomService implements CreateClassroomUseCase {

    private static final int MAX_NAME_LENGTH = 100;
    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_DESCRIPTION_LENGTH = 500;
    private static final int MIN_PASSCODE_LENGTH = 4;

    private final ClassroomAggregateFactory classroomAggregateFactory;
    private final ClassroomAppRepository classroomAppRepository;
    private final ClassroomUserDomainPort classroomUserDomainPort;

    public CreateClassroomService(
            ClassroomAggregateFactory classroomAggregateFactory,
            ClassroomAppRepository classroomAppRepository,
            ClassroomUserDomainPort classroomUserDomainPort
    ) {
        this.classroomAggregateFactory = classroomAggregateFactory;
        this.classroomAppRepository = classroomAppRepository;
        this.classroomUserDomainPort = classroomUserDomainPort;
    }

    public Result<CreateClassroomData, CreateClassroomError> execute(UUID userId, CreateClassroomCommand command) {
        if (userId == null) {
            return Result.fail(CreateClassroomError.INVALID_INSTRUCTOR);
        }

        if (!classroomUserDomainPort.existsByUserId(userId)) {
            return Result.fail(CreateClassroomError.USERID_NOT_FOUND);
        }

        if (command.name() == null || command.name().isBlank()
                || command.name().length() < MIN_NAME_LENGTH
                || command.name().length() > MAX_NAME_LENGTH) {
            return Result.fail(CreateClassroomError.INVALID_NAME);
        }

        if (command.description() != null && command.description().length() > MAX_DESCRIPTION_LENGTH) {
            return Result.fail(CreateClassroomError.INVALID_DESCRIPTION);
        }

        if (command.maxStudents() < ClassroomSettings.MIN_STUDENTS
                || command.maxStudents() > ClassroomSettings.MAX_STUDENTS) {
            return Result.fail(CreateClassroomError.INVALID_MAX_STUDENTS);
        }

        if (command.passcode() != null
                && (command.passcode().isBlank() || command.passcode().length() < MIN_PASSCODE_LENGTH)) {
            return Result.fail(CreateClassroomError.INVALID_PASSCODE);
        }

        ClassroomAggregate aggregate = classroomAggregateFactory.create(
                userId,
                command.name(),
                command.description(),
                command.maxStudents(),
                command.requireApproval(),
                command.passcode()
        );

        classroomAppRepository.save(aggregate);

        return Result.ok(CreateClassroomData.from(aggregate));
    }
}
