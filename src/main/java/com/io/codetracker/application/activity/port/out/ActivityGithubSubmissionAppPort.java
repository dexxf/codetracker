package com.io.codetracker.application.activity.port.out;

import com.io.codetracker.application.activity.result.ActivityData;

import java.util.List;

public interface ActivityGithubSubmissionAppPort {
    List<ActivityData> getUnsubmittedRepositoryActivity(String classroomId, String userId);
}
