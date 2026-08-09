package com.io.kira.application.classroom.port.out;

import com.io.kira.application.classroom.result.ClassroomStudentData;
import com.io.kira.domain.classroom.entity.ClassroomStudent;

import java.util.List;

public interface ClassroomStudentUserAppPort {
    List<ClassroomStudentData> addUserData(List<ClassroomStudent> classroomStudents);
}
