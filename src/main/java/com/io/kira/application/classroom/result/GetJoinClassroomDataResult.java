package com.io.kira.application.classroom.result;

public record GetJoinClassroomDataResult(ClassroomData classroom, Long studentCount, long maxStudent) {
}