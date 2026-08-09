package com.io.kira.application.activity.port.out;


import java.util.UUID;
import com.io.kira.application.activity.result.ActivityDetailsData;

import java.util.List;

public interface ActivityGithubSubmissionAppPort {
    List<ActivityDetailsData> getUnsubmittedRepositoryActivity(UUID classroomId, UUID userId);
}

