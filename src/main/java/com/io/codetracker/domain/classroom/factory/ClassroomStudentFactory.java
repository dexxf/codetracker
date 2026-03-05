package com.io.codetracker.domain.classroom.factory;

import com.io.codetracker.domain.classroom.entity.ClassroomStudent;
import com.io.codetracker.domain.classroom.valueObject.StudentStatus;

public interface ClassroomStudentFactory {
    ClassroomStudent create(String classroomId, String studentUserId, StudentStatus status);
}