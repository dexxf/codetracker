package com.io.codetracker.application.classroom.result;

import com.io.codetracker.domain.classroom.entity.ClassroomStudent;
import com.io.codetracker.domain.classroom.valueObject.StudentStatus;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public record ClassroomStudentData(String classroomId, String firstName, String lastName, String profileUrl, String studentUserId, StudentStatus status, ZonedDateTime lastActiveAt,
                                   ZonedDateTime joinedAt, ZonedDateTime leftAt) {

    public static ClassroomStudentData from(ClassroomStudent classroomStudent,String firstName,String lastName, String profileUrl) {
        if (classroomStudent == null) return null;

        return new ClassroomStudentData(
                classroomStudent.getClassroomId(),
                firstName,
                lastName,
                profileUrl,
                classroomStudent.getStudentUserId(),
                classroomStudent.getStatus(),
                ZonedDateTime.ofInstant(classroomStudent.getLastActiveAt(), ZoneOffset.UTC),
                ZonedDateTime.ofInstant(classroomStudent.getJoinedAt(), ZoneOffset.UTC),
                classroomStudent.getLeftAt() != null ? ZonedDateTime.ofInstant(classroomStudent.getLeftAt(), ZoneOffset.UTC) : null
        );
    }
}