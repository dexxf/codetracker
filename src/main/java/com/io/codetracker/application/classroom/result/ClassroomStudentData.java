package com.io.codetracker.application.classroom.result;

import com.io.codetracker.domain.classroom.entity.ClassroomStudent;
import com.io.codetracker.domain.classroom.valueObject.StudentStatus;

import java.time.LocalDateTime;

public record ClassroomStudentData(String classroomId, String firstName,String lastName,String profileUrl,String studentUserId, StudentStatus status, LocalDateTime lastActiveAt,
                                   LocalDateTime joinedAt, LocalDateTime leftAt) {

    public static ClassroomStudentData from(ClassroomStudent classroomStudent,String firstName,String lastName, String profileUrl) {
        if (classroomStudent == null) return null;

        return new ClassroomStudentData(
                classroomStudent.getClassroomId(),
                firstName,
                lastName,
                profileUrl,
                classroomStudent.getStudentUserId(),
                classroomStudent.getStatus(),
                classroomStudent.getLastActiveAt(),
                classroomStudent.getJoinedAt(),
                classroomStudent.getLeftAt()
        );
    }
}