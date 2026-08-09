package com.io.kira.domain.classroom.aggregate;

import com.io.kira.domain.classroom.entity.Classroom;
import com.io.kira.domain.classroom.entity.ClassroomSettings;

public record ClassroomAggregate(Classroom classroom, ClassroomSettings settings) {
}
