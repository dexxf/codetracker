package com.io.codetracker.infrastructure.classroom.factory;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.io.codetracker.domain.classroom.aggregate.ClassroomAggregate;
import com.io.codetracker.domain.classroom.entity.Classroom;
import com.io.codetracker.domain.classroom.entity.ClassroomSettings;
import com.io.codetracker.domain.classroom.factory.ClassroomAggregateFactory;
import com.io.codetracker.domain.classroom.service.CodeGenerator;
import com.io.codetracker.domain.classroom.valueObject.ClassroomStatus;

@Component
public class DefaultClassroomAggregateFactory implements ClassroomAggregateFactory {

    private final CodeGenerator codeGenerator;

    public DefaultClassroomAggregateFactory(CodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
    }

    @Override
    public ClassroomAggregate create(
            UUID instructorUserId,
            String name,
            String description,
            int maxStudents,
            boolean requireApproval,
            String passcode
    ) {
        UUID classroomId = UUID.randomUUID();

        Classroom classroom = new Classroom(
                classroomId,
                instructorUserId,
                name,
                description,
                codeGenerator.generateCode(),
                ClassroomStatus.ACTIVE,
                Instant.now(),
                Instant.now()
        );

        ClassroomSettings settings = new ClassroomSettings(classroomId, requireApproval, passcode, maxStudents);

        return new ClassroomAggregate(classroom, settings);
    }
}
