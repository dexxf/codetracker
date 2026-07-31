package com.io.codetracker.application.activity.port.out;


import java.util.UUID;
import com.io.codetracker.application.activity.result.ActivityDetailsData;

import java.util.List;

public interface ActivityGithubSubmissionAppPort {
    List<ActivityDetailsData> getUnsubmittedRepositoryActivity(UUID classroomId, UUID userId);
}

