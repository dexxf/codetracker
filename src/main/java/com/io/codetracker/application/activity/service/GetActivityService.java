package com.io.codetracker.application.activity.service;

import com.io.codetracker.application.activity.port.out.ActivityClassroomAppPort;
import com.io.codetracker.application.activity.command.GetActivityCommand;
import com.io.codetracker.adapter.activity.in.dto.response.GetActivityResponse;
import com.io.codetracker.application.activity.port.out.ActivityAppRepository;
import com.io.codetracker.application.activity.result.ActivityData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class GetActivityService {

    private final ActivityAppRepository activityAppRepository;
    private final ActivityClassroomAppPort activityClassroomAppPort;

    public GetActivityResponse execute(GetActivityCommand command) {
            if (!activityClassroomAppPort.existsByClassroomId(command.classroomId())) {
                return GetActivityResponse.fail("Classroom does not exists..");
            }

            var activities =  activityAppRepository.findByClassroomId(command.classroomId(), command.instructorUserId())
                    .stream().map(ActivityData::from).toList();

            return GetActivityResponse.success(activities);
    }

}
