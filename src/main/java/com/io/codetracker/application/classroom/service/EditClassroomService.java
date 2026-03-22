package com.io.codetracker.application.classroom.service;

import com.io.codetracker.application.classroom.command.EditClassroomCommand;
import com.io.codetracker.application.classroom.error.EditClassroomError;
import com.io.codetracker.application.classroom.port.in.EditClassroomUseCase;
import com.io.codetracker.application.classroom.port.out.ClassroomAppRepository;
import com.io.codetracker.application.classroom.port.out.ClassroomStudentAppRepository;
import com.io.codetracker.application.classroom.result.ClassroomData;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.classroom.entity.Classroom;
import com.io.codetracker.domain.classroom.entity.ClassroomSettings;
import com.io.codetracker.domain.classroom.exception.InvalidStudentQuantityException;
import com.io.codetracker.domain.classroom.result.EditClassroomResult;
import com.io.codetracker.domain.classroom.service.UpdateClassroomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EditClassroomService implements EditClassroomUseCase {

    private final UpdateClassroomService updateClassroomService;
    private final ClassroomAppRepository classroomAppRepository;
    private final ClassroomStudentAppRepository classroomStudentAppRepository;

    public Result<ClassroomData, EditClassroomError> execute(EditClassroomCommand command) {
        boolean classroomExists = classroomAppRepository.existsByClassroomId(command.classroomId());
        if (!classroomExists) {
            return Result.fail(EditClassroomError.CLASSROOM_NOT_FOUND);
        }

        boolean isInstructor = classroomAppRepository.existsByClassroomIdAndInstructorUserId(
            command.classroomId(),
            command.userId()
        );
        if (!isInstructor) {
            return Result.fail(EditClassroomError.NOT_INSTRUCTOR);
        }

        ClassroomSettings classroomSettings = classroomAppRepository.findSettingsByClassroomId(command.classroomId())
            .orElse(null);
        if (classroomSettings == null) {
            return Result.fail(EditClassroomError.CLASSROOM_SETTINGS_NOT_FOUND);
        }

        long enrolledStudents = classroomStudentAppRepository.countByClassroomId(command.classroomId());
        if (command.maxStudents() < enrolledStudents) {
            return Result.fail(EditClassroomError.MAX_STUDENTS_LESS_THAN_ENROLLED);
        }

        try {
            classroomSettings.updateMaxStudents(command.maxStudents());
        } catch (InvalidStudentQuantityException ex) {
            return Result.fail(EditClassroomError.INVALID_MAX_STUDENTS);
        }

        Result<Classroom, EditClassroomResult> result = updateClassroomService.updateAndValidate(
            command.classroomId(),
            command.name(),
            command.description()
        );

        if (!result.success()) {
            return Result.fail(EditClassroomError.from(result.error()));
        }

        Classroom updatedClassroom = result.data();
        classroomAppRepository.updateClassroom(updatedClassroom, classroomSettings);
        return Result.ok(ClassroomData.from(updatedClassroom));
    }
}
