package com.io.kira.domain.classroom.result;

import com.io.kira.domain.classroom.entity.Classroom;
import com.io.kira.domain.classroom.entity.ClassroomSettings;

public record ClassroomJoinValidationResult (Classroom classroom, ClassroomSettings classroomSettings){
}