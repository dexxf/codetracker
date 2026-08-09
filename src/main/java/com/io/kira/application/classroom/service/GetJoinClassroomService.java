package com.io.kira.application.classroom.service;


import java.util.UUID;
import com.io.kira.application.classroom.port.in.GetJoinClassroomUseCase;
import com.io.kira.application.classroom.port.out.ClassroomAppRepository;
import com.io.kira.application.classroom.port.out.ClassroomStudentAppRepository;
import com.io.kira.application.classroom.result.ClassroomData;
import com.io.kira.application.classroom.result.GetJoinClassroomDataResult;
import com.io.kira.domain.classroom.aggregate.ClassroomAggregate;
import com.io.kira.domain.classroom.entity.ClassroomStudent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GetJoinClassroomService implements GetJoinClassroomUseCase {

    private final ClassroomStudentAppRepository classroomStudentRepository;
    private final ClassroomAppRepository classroomRepository;

    public GetJoinClassroomService(
            ClassroomStudentAppRepository classroomStudentRepository,
            ClassroomAppRepository classroomRepository) {
        this.classroomStudentRepository = classroomStudentRepository;
        this.classroomRepository = classroomRepository;
    }

    public List<GetJoinClassroomDataResult> execute(UUID userId) {
        List<ClassroomStudent> enrollments = classroomStudentRepository
                .findActiveEnrollmentsWithActiveClassroom(userId);

        List<UUID> classroomIds = enrollments.stream()
                .map(ClassroomStudent::getClassroomId)
                .distinct()
                .toList();

        List<ClassroomAggregate> classrooms = classroomRepository.findAllById(classroomIds);
        Map<UUID, Long> counts = classroomStudentRepository.countActiveClassroomStudentByClassroomIds(classroomIds);

        return classrooms.stream()
                .map(aggregate -> new GetJoinClassroomDataResult(
                        ClassroomData.from(aggregate.classroom()),
                        counts.getOrDefault(aggregate.classroom().getClassroomId(), 0L),
                        aggregate.settings().getMaxStudents()
                ))
                .toList();
    }
}
