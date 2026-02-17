package com.io.codetracker.application.classroom.port.out;

import java.util.List;

import com.io.codetracker.domain.classroom.entity.Classroom;
import com.io.codetracker.domain.classroom.entity.ClassroomSettings;

public interface ClassroomAppRepository {
    void saveClassroom(Classroom classroom, ClassroomSettings classroomSettings);
    List<Classroom> findByInstructorUserId(String instructorUserId);
}