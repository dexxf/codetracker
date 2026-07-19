package com.io.codetracker.application.activity.port.out;

import com.io.codetracker.application.activity.result.StudentActivityInfoData;
import com.io.codetracker.application.activity.result.StudentActivityInfoStudentData;

import java.util.List;
import java.util.UUID;

public interface StudentActivityInfoAppRepository {
    List<StudentActivityInfoStudentData> findClassroomStudents(UUID classroomId);
    List<StudentActivityInfoData> findStudentActivityInfos(UUID classroomId);
}
