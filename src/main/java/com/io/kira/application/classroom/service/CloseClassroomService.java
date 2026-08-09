package com.io.kira.application.classroom.service;

import com.io.kira.application.classroom.command.CloseClassroomCommand;
import com.io.kira.application.classroom.error.CloseClassroomError;
import com.io.kira.application.classroom.port.in.CloseClassroomUseCase;
import com.io.kira.application.classroom.port.out.ClassroomAppRepository;
import com.io.kira.application.classroom.result.ClassroomData;
import com.io.kira.common.result.Result;
import com.io.kira.domain.classroom.aggregate.ClassroomAggregate;
import com.io.kira.domain.classroom.entity.Classroom;
import com.io.kira.domain.classroom.entity.ClassroomSettings;
import com.io.kira.domain.classroom.valueObject.ClassroomStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CloseClassroomService implements CloseClassroomUseCase {

    private final ClassroomAppRepository classroomAppRepository;

    public Result<ClassroomData, CloseClassroomError> execute(CloseClassroomCommand command) {
        boolean isInstructor = classroomAppRepository.existsByClassroomIdAndInstructorUserId(
            command.classroomId(),
            command.userId()
        );
        if (!isInstructor) {
            return Result.fail(CloseClassroomError.NOT_INSTRUCTOR);
        }

        Optional<ClassroomAggregate> classroomOptional = classroomAppRepository.findByClassroomId(command.classroomId());
        if (classroomOptional.isEmpty()) {
            return Result.fail(CloseClassroomError.CLASSROOM_NOT_FOUND);
        }

        ClassroomAggregate aggregate = classroomOptional.get();
        Classroom classroom = aggregate.classroom();
        if (classroom.getStatus() == ClassroomStatus.CLOSED) {
            return Result.fail(CloseClassroomError.ALREADY_CLOSED);
        }

        classroom.close();

        ClassroomSettings settings = aggregate.settings();

        classroomAppRepository.update(new ClassroomAggregate(classroom, settings));
        return Result.ok(ClassroomData.from(classroom));
    }
}
