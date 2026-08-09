package com.io.kira.adapter.classroom.in.dto.response;

import com.io.kira.application.classroom.result.ClassroomRecentActivityData;

import java.util.List;

public record GetClassroomRecentActivitiesResponse(List<ClassroomRecentActivityData> data, String error) {

    public static GetClassroomRecentActivitiesResponse success(List<ClassroomRecentActivityData> data) {
        return new GetClassroomRecentActivitiesResponse(data, null);
    }

    public static GetClassroomRecentActivitiesResponse fail(String error) {
        return new GetClassroomRecentActivitiesResponse(null, error);
    }
}
