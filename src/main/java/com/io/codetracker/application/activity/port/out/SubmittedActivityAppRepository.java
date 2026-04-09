package com.io.codetracker.application.activity.port.out;

import com.io.codetracker.application.activity.result.SubmittedActivityData;
import com.io.codetracker.application.activity.result.SubmittedActivityStudentData;

import java.util.List;

public interface SubmittedActivityAppRepository {
    List<SubmittedActivityStudentData> findClassroomStudents(String classroomId);
    List<SubmittedActivityData> findSubmittedActivities(String classroomId);
}
