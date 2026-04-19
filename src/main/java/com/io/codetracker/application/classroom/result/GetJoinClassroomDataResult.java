package com.io.codetracker.application.classroom.result;

public record GetJoinClassroomDataResult(ClassroomData classroom, Long studentCount, int maxStudent) {
}