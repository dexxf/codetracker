package com.io.codetracker.application.classroom.service;

import com.io.codetracker.application.classroom.command.GetClassroomRecentActivitiesCommand;
import com.io.codetracker.application.classroom.error.GetClassroomRecentActivitiesError;
import com.io.codetracker.application.classroom.port.in.GetClassroomRecentActivitiesUseCase;
import com.io.codetracker.application.classroom.port.out.ClassroomAppRepository;
import com.io.codetracker.application.classroom.port.out.ClassroomRecentActivityAppRepository;
import com.io.codetracker.application.classroom.port.out.ClassroomStudentAppRepository;
import com.io.codetracker.application.classroom.result.ClassroomRecentActivityData;
import com.io.codetracker.common.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetClassroomRecentActivitiesService implements GetClassroomRecentActivitiesUseCase {

    private final ClassroomAppRepository classroomAppRepository;
    private final ClassroomStudentAppRepository classroomStudentAppRepository;
    private final ClassroomRecentActivityAppRepository classroomRecentActivityAppRepository;

    @Override
    public Result<List<ClassroomRecentActivityData>, GetClassroomRecentActivitiesError> execute(GetClassroomRecentActivitiesCommand command) {
        if (!classroomAppRepository.existsByClassroomId(command.classroomId())) {
            return Result.fail(GetClassroomRecentActivitiesError.CLASSROOM_NOT_FOUND);
        }

        boolean isInstructor = classroomAppRepository.existsByClassroomIdAndInstructorUserId(command.classroomId(), command.userId());
        boolean isActiveStudent = classroomStudentAppRepository.existsByClassroomIdAndStudentUserId(command.classroomId(), command.userId());

        if (!isInstructor && !isActiveStudent) {
            return Result.fail(GetClassroomRecentActivitiesError.USER_NOT_CLASSROOM_MEMBER);
        }

        int limit = Math.max(1, Math.min(command.limit(), 100));
        List<ClassroomRecentActivityData> recentActivities = classroomRecentActivityAppRepository.findRecentActivities(command.classroomId(), limit);
        return Result.ok(recentActivities);
    }
}
