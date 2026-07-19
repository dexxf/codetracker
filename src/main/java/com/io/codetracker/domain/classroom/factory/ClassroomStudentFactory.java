package com.io.codetracker.domain.classroom.factory;


import java.util.UUID;
import com.io.codetracker.domain.classroom.entity.ClassroomStudent;
import com.io.codetracker.domain.classroom.valueObject.StudentStatus;

public interface ClassroomStudentFactory {
    ClassroomStudent create(String classroomId, UUID studentUserId, StudentStatus status);
}
