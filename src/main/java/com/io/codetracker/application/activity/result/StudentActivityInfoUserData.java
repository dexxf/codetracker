package com.io.codetracker.application.activity.result;

import java.util.List;

public record StudentActivityInfoUserData(
        String userId,
        String firstName,
        String lastName,
        String profileUrl,
        List<StudentActivityInfoData> studentActivities
) {
}
