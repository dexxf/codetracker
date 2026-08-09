package com.io.kira.application.activity.port.out;

import com.io.kira.application.activity.result.StudentSubmissionDetailsData;
import com.io.kira.application.activity.result.StudentSummaryData;

import java.util.List;
import java.util.UUID;

public interface StudentActivityInfoAppRepository {
    List<StudentSummaryData> findClassroomStudents(UUID classroomId);
    List<StudentSubmissionDetailsData> findStudentActivityInfos(UUID classroomId);
}
