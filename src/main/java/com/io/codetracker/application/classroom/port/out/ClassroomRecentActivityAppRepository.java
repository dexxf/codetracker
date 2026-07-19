package com.io.codetracker.application.classroom.port.out;

import com.io.codetracker.application.classroom.result.ClassroomRecentActivityData;

import java.util.List;
import java.util.UUID;

public interface ClassroomRecentActivityAppRepository {
    List<ClassroomRecentActivityData> findRecentActivities(UUID classroomId, int limit);
}
