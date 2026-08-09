package com.io.kira.application.classroom.service;


import java.util.UUID;
import java.util.List;
import java.util.Map;

import com.io.kira.application.classroom.error.SimpleClassroomError;
import com.io.kira.application.classroom.port.in.GetClassroomUseCase;
import com.io.kira.application.classroom.port.out.ClassroomAppRepository;
import com.io.kira.application.classroom.port.out.ClassroomStudentAppRepository;
import com.io.kira.application.classroom.result.GetClassroomsProfessorData;
import com.io.kira.common.result.Result;
import com.io.kira.domain.classroom.aggregate.ClassroomAggregate;
import org.springframework.stereotype.Service;


@Service
public class GetClassroomsService implements GetClassroomUseCase {
    
    private final ClassroomStudentAppRepository classroomStudentAppRepository;
    private final ClassroomAppRepository classroomAppRepository;

    public GetClassroomsService(ClassroomStudentAppRepository classroomStudentAppRepository, ClassroomAppRepository classroomAppRepository) {
        this.classroomStudentAppRepository = classroomStudentAppRepository;
        this.classroomAppRepository = classroomAppRepository;
    }

    public Result<List<GetClassroomsProfessorData>, SimpleClassroomError> execute(UUID userId) {
        List<ClassroomAggregate> classroomList = classroomAppRepository.findByInstructorUserId(userId);
        if (classroomList.isEmpty()) {
            return Result.fail(SimpleClassroomError.NO_CLASSROOM_FOUND);
        }

        Map<UUID, Long> classroomWithCount = classroomStudentAppRepository
                .countActiveClassroomStudentByClassroomIds(classroomList.stream().map(
                        aggregate -> aggregate.classroom().getClassroomId()).toList());


        List<GetClassroomsProfessorData> dataList = classroomList.stream()
                .map(aggregate -> GetClassroomsProfessorData.from(
                        aggregate,
                        classroomWithCount.getOrDefault(aggregate.classroom().getClassroomId(), 0L)
                    ))
                .toList();

        return Result.ok(dataList);
    }
}
