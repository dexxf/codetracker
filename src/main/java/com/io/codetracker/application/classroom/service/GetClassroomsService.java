package com.io.codetracker.application.classroom.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.io.codetracker.application.classroom.port.out.ClassroomAppRepository;
import com.io.codetracker.application.classroom.response.GetClassroomsResponse;
import com.io.codetracker.domain.classroom.entity.Classroom;

@Service
public class GetClassroomsService {
    
    private final ClassroomAppRepository classroomAppRepository;

    public GetClassroomsService(ClassroomAppRepository classroomAppRepository) {
        this.classroomAppRepository = classroomAppRepository;
    }

    public GetClassroomsResponse execute(String userId) {
        List<Classroom> data = classroomAppRepository.findByInstructorUserId(userId);
        return GetClassroomsResponse.ok(data);
    }
      
}