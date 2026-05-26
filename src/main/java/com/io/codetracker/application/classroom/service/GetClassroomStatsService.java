package com.io.codetracker.application.classroom.service;

import com.io.codetracker.application.classroom.port.in.GetClassroomStatsUseCase;
import com.io.codetracker.application.classroom.result.ClassroomStats;
import com.io.codetracker.common.result.Result;
import org.springframework.stereotype.Service;

import com.io.codetracker.application.classroom.command.ClassroomStatsCommand;
import com.io.codetracker.application.classroom.error.SimpleClassroomError;
import com.io.codetracker.application.classroom.port.out.ClassroomActivityAppPort;
import com.io.codetracker.application.classroom.port.out.ClassroomAppRepository;
import com.io.codetracker.application.classroom.port.out.ClassroomStudentAppRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GetClassroomStatsService implements GetClassroomStatsUseCase {

    private final ClassroomAppRepository classroomAppRepository;
    private final ClassroomStudentAppRepository classroomStudentAppRepository;
    private final ClassroomActivityAppPort classroomActivityAppPort;

    public Result<ClassroomStats, SimpleClassroomError> execute(ClassroomStatsCommand command) {
        boolean isOwner = classroomAppRepository.existsByClassroomIdAndInstructorUserId(command.classroomId(),command.userId());
        if(!isOwner) {
            return Result.fail(SimpleClassroomError.USER_NOT_CLASSROOM_INSTRUCTOR);
        }

        long totalStudents = classroomStudentAppRepository.countActiveClassroomStudentByClassroomId(command.classroomId());
        long totalActivities = classroomActivityAppPort.countByClassroomId(command.classroomId());
        long totalActiveActivities = classroomActivityAppPort.countActiveActivitiesByClassroomId(command.classroomId());

        return Result.ok(new ClassroomStats(totalActivities,totalActiveActivities, totalStudents));
    }

}
