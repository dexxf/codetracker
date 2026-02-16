package com.io.codetracker.domain.classroom.result;

import com.io.codetracker.domain.classroom.entity.Classroom;
import com.io.codetracker.domain.classroom.entity.ClassroomSettings;

public record ClassroomCreationEntity(Classroom classroom, ClassroomSettings settings) {
    
}