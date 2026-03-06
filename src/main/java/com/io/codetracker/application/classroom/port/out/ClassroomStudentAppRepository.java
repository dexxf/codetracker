package com.io.codetracker.application.classroom.port.out;

import com.io.codetracker.domain.classroom.entity.ClassroomStudent;

public interface ClassroomStudentAppRepository {
    boolean save(ClassroomStudent classroomStudent);
}