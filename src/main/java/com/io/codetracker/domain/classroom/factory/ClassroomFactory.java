package com.io.codetracker.domain.classroom.factory;

import com.io.codetracker.domain.classroom.entity.Classroom;
import com.io.codetracker.domain.classroom.entity.ClassroomSettings;

public interface ClassroomFactory {
    Classroom createClassroom(String instructorUserId, String name, String description);
    ClassroomSettings createClassroomSetting(String classroomId, int maxStudents, boolean requireApproval, String passcode);
}