package com.io.codetracker.application.classroom.service;

import com.io.codetracker.application.classroom.command.DeleteClassroomCommand;
import com.io.codetracker.application.classroom.port.in.DeleteClassroomUseCase;
import com.io.codetracker.application.classroom.port.out.ClassroomAppRepository;
import com.io.codetracker.application.classroom.result.DeleteClassroomResult;
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