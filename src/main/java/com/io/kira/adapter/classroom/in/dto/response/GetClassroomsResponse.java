package com.io.kira.adapter.classroom.in.dto.response;

import java.util.List;

import com.io.kira.application.classroom.result.GetClassroomsProfessorData;

public record GetClassroomsResponse(List<GetClassroomsProfessorData> data, String message) {

    public static GetClassroomsResponse ok(List<GetClassroomsProfessorData> data) {
        return new GetClassroomsResponse(data, "Successfully fetched user's classrooms");
    }
    public static GetClassroomsResponse fail(String message) {
        return new GetClassroomsResponse(null, message);
    }
}
