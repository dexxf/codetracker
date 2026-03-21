package com.io.codetracker.application.classroom.result;

public record GetJoinClassroomDataResult (ClassroomData classroom, int studentCount, int maxStudent) {
}