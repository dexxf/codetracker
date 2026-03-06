package com.io.codetracker.application.classroom.result;

import com.io.codetracker.domain.classroom.entity.Classroom;

public record GetJoinClassroomDataResult (Classroom classroom, int studentCount){
}