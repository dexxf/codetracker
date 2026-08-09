package com.io.kira.application.classroom.service;

import com.io.kira.application.classroom.command.DeleteClassroomCommand;
import com.io.kira.application.classroom.port.in.DeleteClassroomUseCase;
import com.io.kira.application.classroom.port.out.ClassroomAppRepository;
import com.io.kira.application.classroom.result.DeleteClassroomResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteClassroomService implements DeleteClassroomUseCase {

    private final ClassroomAppRepository classroomAppRepository;

    @Override
    public DeleteClassroomResult execute(DeleteClassroomCommand command) {
        boolean classroomExists = classroomAppRepository.existsByClassroomId(command.classroomId());
        if (!classroomExists) {
            return DeleteClassroomResult.CLASSROOM_NOT_FOUND;
        }

        boolean isInstructor = classroomAppRepository.existsByClassroomIdAndInstructorUserId(
            command.classroomId(),
            command.userId()
        );
        if (!isInstructor) {
            return DeleteClassroomResult.NOT_INSTRUCTOR;
        }

        classroomAppRepository.deleteByClassroomId(command.classroomId());
        return DeleteClassroomResult.SUCCESS;
    }
}