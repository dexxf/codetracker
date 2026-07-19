package com.io.codetracker.application.classroom.port.out;


import java.util.UUID;
import java.util.List;
import java.util.Optional;

import com.io.codetracker.domain.classroom.entity.Classroom;
import com.io.codetracker.domain.classroom.entity.ClassroomSettings;

public interface ClassroomAppRepository {
    void saveClassroom(Classroom classroom, ClassroomSettings classroomSettings);
    void deleteByClassroomId(String classroomId);
    List<Classroom> findByInstructorUserId(UUID instructorUserId);
    List<Classroom> findAllById(List<String> classroomIds);
    Optional<Classroom> findByClassroomId(String classroomId);
    Optional<ClassroomSettings> findSettingsByClassroomId(String classroomId);
    boolean existsByClassroomId(String classroomId);
    boolean existsByClassroomIdAndInstructorUserId(String classroomId, UUID instructorUserId);
    Integer findMaxStudentByClassroomId(String classroomId);
    void updateClassroom(Classroom updatedClassroom, ClassroomSettings classroomSettings);
}
