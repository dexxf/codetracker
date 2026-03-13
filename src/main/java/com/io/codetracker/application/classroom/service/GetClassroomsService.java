package com.io.codetracker.application.classroom.service;

import java.util.List;
import java.util.Map;

import com.io.codetracker.application.classroom.port.in.GetClassroomUseCase;
import com.io.codetracker.application.classroom.port.out.ClassroomAppRepository;
import com.io.codetracker.application.classroom.port.out.ClassroomStudentAppRepository;
import com.io.codetracker.application.classroom.result.GetClassroomsProfessorData;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.classroom.entity.Classroom;
import org.springframework.stereotype.Service;


@Service
public class GetClassroomsService implements GetClassroomUseCase {
    
    private final ClassroomStudentAppRepository classroomStudentAppRepository;
    private final ClassroomAppRepository classroomAppRepository;

    public GetClassroomsService(ClassroomStudentAppRepository classroomStudentAppRepository, ClassroomAppRepository classroomAppRepository) {
        this.classroomStudentAppRepository = classroomStudentAppRepository;
        this.classroomAppRepository = classroomAppRepository;
    }

    public Result<List<GetClassroomsProfessorData>, String> execute(String userId) {
        List<Classroom> classroomList = classroomAppRepository.findByInstructorUserId(userId);
        if (classroomList.isEmpty()) {
            return Result.fail("No classroom found.");
        }

        Map<String, Integer> classroomWithCount = classroomStudentAppRepository
                .countByClassroomIds(classroomList.stream().map(Classroom::getClassroomId).toList());

        List<GetClassroomsProfessorData> dataList = classroomList.stream()
                .map(classroom -> GetClassroomsProfessorData.from(
                        classroom,
                        classroomWithCount.getOrDefault(classroom.getClassroomId(), 0)
                ))
                .toList();

        return Result.ok(dataList);
    }
}