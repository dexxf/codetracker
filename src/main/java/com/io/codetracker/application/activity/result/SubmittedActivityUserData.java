package com.io.codetracker.application.activity.result;

import java.util.List;

public record SubmittedActivityUserData(
        String userId,
        String firstName,
        String lastName,
        String profileUrl,
        List<SubmittedActivityData> submittedActivities
) {
}
