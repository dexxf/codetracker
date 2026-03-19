package com.io.codetracker.application.classroom.service;

import com.io.codetracker.application.classroom.command.CloseClassroomCommand;
import com.io.codetracker.application.classroom.error.CloseClassroomError;
import com.io.codetracker.application.classroom.port.in.CloseClassroomUseCase;
import com.io.codetracker.application.classroom.port.out.ClassroomAppRepository;
import com.io.codetracker.application.classroom.result.ClassroomData;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.classroom.entity.Classroom;
import com.io.codetracker.domain.classroom.entity.ClassroomSettings;
import com.io.codetracker.domain.classroom.valueObject.ClassroomStatus;
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

        Optional<Classroom> classroomOptional = classroomAppRepository.findByClassroomId(command.classroomId());
        if (classroomOptional.isEmpty()) {
            return Result.fail(CloseClassroomError.CLASSROOM_NOT_FOUND);
        }

        Classroom classroom = classroomOptional.get();
        if (classroom.getStatus() == ClassroomStatus.CLOSED) {
            return Result.fail(CloseClassroomError.ALREADY_CLOSED);
        }

        classroom.close();

        Optional<ClassroomSettings> settings = classroomAppRepository.findSettingsByClassroomId(classroom.getClassroomId());
        if(settings.isEmpty()) {
            return Result.fail(CloseClassroomError.CLASSROOM_SETTINGS_NOT_FOUND);
        }

        classroomAppRepository.saveClassroom(classroom, settings.get());
        return Result.ok(ClassroomData.from(classroom));
    }
}
