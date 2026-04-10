package com.io.codetracker.application.classroom.port.out;

import com.io.codetracker.application.classroom.result.ClassroomRecentActivityData;

import java.util.List;

public interface ClassroomRecentActivityAppRepository {
    List<ClassroomRecentActivityData> findRecentActivities(String classroomId, int limit);
}
