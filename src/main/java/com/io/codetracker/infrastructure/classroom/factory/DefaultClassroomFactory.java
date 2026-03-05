package com.io.codetracker.infrastructure.classroom.factory;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.io.codetracker.common.id.IDGenerator;
import com.io.codetracker.domain.classroom.entity.Classroom;
import com.io.codetracker.domain.classroom.entity.ClassroomSettings;
import com.io.codetracker.domain.classroom.factory.ClassroomFactory;
import com.io.codetracker.domain.classroom.service.CodeGenerator;
import com.io.codetracker.domain.classroom.valueObject.ClassroomStatus;

@Component
public class DefaultClassroomFactory implements ClassroomFactory {

    private final IDGenerator classroomIdGenerator;
    private final CodeGenerator codeGenerator;

    public DefaultClassroomFactory(@Qualifier("classroomIDGenerator") IDGenerator classroomIdGenerator,CodeGenerator codeGenerator) {
        this.classroomIdGenerator = classroomIdGenerator;
        this.codeGenerator = codeGenerator;
    }

    @Override
    public Classroom createClassroom(String instructorUserId, String name,String description) {
        return new Classroom(classroomIdGenerator.generate(), instructorUserId, name, description,
             codeGenerator.generateCode(),ClassroomStatus.ACTIVE,LocalDateTime.now(), LocalDateTime.now());
    }

    @Override
    public ClassroomSettings createClassroomSetting(String classroomId, int maxStudents, boolean requireApproval, String passcode) {
        return new ClassroomSettings(classroomId, requireApproval, passcode, maxStudents);
    }

}
