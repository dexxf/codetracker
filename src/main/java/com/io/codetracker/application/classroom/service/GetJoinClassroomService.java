package com.io.codetracker.application.classroom.service;

import com.io.codetracker.application.classroom.port.out.ClassroomAppRepository;
import com.io.codetracker.application.classroom.port.out.ClassroomStudentAppRepository;
import com.io.codetracker.application.classroom.result.GetJoinClassroomDataResult;
import com.io.codetracker.domain.classroom.entity.Classroom;
import com.io.codetracker.domain.classroom.entity.ClassroomStudent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GetJoinClassroomService {

    private final ClassroomStudentAppRepository classroomStudentRepository;
    private final ClassroomAppRepository classroomRepository;

    public GetJoinClassroomService(
            ClassroomStudentAppRepository classroomStudentRepository,
            ClassroomAppRepository classroomRepository) {
        this.classroomStudentRepository = classroomStudentRepository;
        this.classroomRepository = classroomRepository;
    }

    public List<GetJoinClassroomDataResult> execute(String userId) {
        List<ClassroomStudent> enrollments = classroomStudentRepository
                .findActiveEnrollmentsWithActiveClassroom(userId);

        List<String> classroomIds = enrollments.stream()
                .map(ClassroomStudent::getClassroomId)
                .distinct()
                .toList();

        List<Classroom> classrooms = classroomRepository.findAllById(classroomIds);
        Map<String, Integer> counts = classroomStudentRepository.countByClassroomIds(classroomIds);

        return classrooms.stream()
                .map(classroom -> new GetJoinClassroomDataResult(
                        classroom,
                        counts.getOrDefault(classroom.getClassroomId(), 0)
                ))
                .toList();
    }
}