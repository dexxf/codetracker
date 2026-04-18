package com.io.codetracker.infrastructure.classroom.factory;

import com.io.codetracker.domain.classroom.entity.ClassroomStudent;
import com.io.codetracker.domain.classroom.factory.ClassroomStudentFactory;
import com.io.codetracker.domain.classroom.valueObject.StudentStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DefaultClassroomStudentFactory implements ClassroomStudentFactory {

    @Override
    public ClassroomStudent create(String classroomId, String studentUserId, StudentStatus status) {
        Instant now = Instant.now();

        return new ClassroomStudent(classroomId,studentUserId, status,now, now, null
        );
    }
}