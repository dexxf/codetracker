package com.io.codetracker.application.classroom.service;

import org.springframework.stereotype.Service;

import com.io.codetracker.application.classroom.command.ClassroomStatsCommand;
import com.io.codetracker.application.classroom.port.in.response.GetClassroomStatsResponse;
import com.io.codetracker.application.classroom.port.out.ClassroomActivityAppPort;
import com.io.codetracker.application.classroom.port.out.ClassroomAppRepository;
import com.io.codetracker.application.classroom.port.out.ClassroomStudentAppRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GetClassroomStatsService {
    

    private final ClassroomAppRepository classroomAppRepository;
    private final ClassroomStudentAppRepository classroomStudentAppRepository;
    private final ClassroomActivityAppPort classroomActivityAppPort;


    public GetClassroomStatsResponse execute(ClassroomStatsCommand command) {
        boolean classroomExists = classroomAppRepository.existsByClassroomId(command.classroomId());
        if (!classroomExists) {
            return GetClassroomStatsResponse.fail(command.classroomId(), "Classroom not found.");
        }

        boolean isOwner = classroomAppRepository.existsByClassroomIdAndInstructorUserId(command.classroomId(),command.userId());
        if(!isOwner) {
            return GetClassroomStatsResponse.fail(command.classroomId(), "You are not the owner of this classroom.");
        }

        long totalStudents = classroomStudentAppRepository.countByClassroomId(command.classroomId());
        long totalActivities = classroomActivityAppPort.countByClassroomId(command.classroomId());
        long totalActiveActivities = classroomActivityAppPort.countActiveActivitiesByClassroomId(command.classroomId());

        return GetClassroomStatsResponse.success(command.classroomId(), totalStudents, totalActivities, totalActiveActivities);
    }

}
