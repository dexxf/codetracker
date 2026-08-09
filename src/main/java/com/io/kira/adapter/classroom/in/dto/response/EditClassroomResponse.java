package com.io.kira.adapter.classroom.in.dto.response;

import com.io.kira.application.classroom.result.ClassroomData;

public record EditClassroomResponse(ClassroomData data, String message) {

    public static EditClassroomResponse ok(ClassroomData data) {
        return new EditClassroomResponse(data, "Successfully updated classroom.");
    }

    public static EditClassroomResponse fail(String message) {
        return new EditClassroomResponse(null, message);
    }
}
