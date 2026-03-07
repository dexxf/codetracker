package com.io.codetracker.application.classroom.service;

import java.util.List;
import java.util.Map;

import com.io.codetracker.application.classroom.port.out.ClassroomAppRepository;
import com.io.codetracker.application.classroom.port.out.ClassroomStudentAppRepository;
import com.io.codetracker.application.classroom.result.GetClassroomsProfessorData;
import com.io.codetracker.domain.classroom.entity.Classroom;
import org.springframework.stereotype.Service;

import com.io.codetracker.application.classroom.port.in.response.GetClassroomsResponse;

@Service
public class GetClassroomsService {
    
    private final ClassroomStudentAppRepository classroomStudentAppRepository;
    private final ClassroomAppRepository classroomAppRepository;

    public GetClassroomsService(ClassroomStudentAppRepository classroomStudentAppRepository, ClassroomAppRepository classroomAppRepository) {
        this.classroomStudentAppRepository = classroomStudentAppRepository;
        this.classroomAppRepository = classroomAppRepository;
    }

    public GetClassroomsResponse execute(String userId) {
        List<Classroom> classroomList = classroomAppRepository.findByInstructorUserId(userId);
        if (classroomList.isEmpty()) {
            return GetClassroomsResponse.fail("No classroom found.");
        }

        Map<String, Integer> classroomWithCount = classroomStudentAppRepository
                .countByClassroomIds(classroomList.stream().map(Classroom::getClassroomId).toList());

        List<GetClassroomsProfessorData> result = classroomList.stream()
                .map(classroom -> GetClassroomsProfessorData.from(
                        classroom,
                        classroomWithCount.getOrDefault(classroom.getClassroomId(), 0)
                ))
                .toList();

        return GetClassroomsResponse.ok(result);
    }
}