package com.io.codetracker.application.activity.port.out;

import com.io.codetracker.application.activity.result.StudentActivityInfoData;
import com.io.codetracker.application.activity.result.StudentActivityInfoStudentData;

import java.util.List;

public interface StudentActivityInfoAppRepository {
    List<StudentActivityInfoStudentData> findClassroomStudents(String classroomId);
    List<StudentActivityInfoData> findStudentActivityInfos(String classroomId);
}
