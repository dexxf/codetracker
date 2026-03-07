package com.io.codetracker.application.classroom.port.out;

import com.io.codetracker.application.classroom.result.ClassroomStudentData;
import com.io.codetracker.domain.classroom.entity.ClassroomStudent;

import java.util.List;

public interface ClassroomStudentUserAppPort {
    List<ClassroomStudentData> addUserData(List<ClassroomStudent> classroomStudents);
}
