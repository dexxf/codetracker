package com.io.codetracker.domain.classroom.aggregate;

import com.io.codetracker.domain.classroom.entity.Classroom;
import com.io.codetracker.domain.classroom.entity.ClassroomSettings;

public record ClassroomAggregate(Classroom classroom, ClassroomSettings settings) {
}
