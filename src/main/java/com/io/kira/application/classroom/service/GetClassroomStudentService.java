package com.io.kira.application.classroom.service;

import com.io.kira.application.classroom.command.GetClassroomStudentCommand;
import com.io.kira.application.classroom.error.SimpleClassroomError;
import com.io.kira.application.classroom.port.in.GetClassroomStudentUseCase;
import com.io.kira.application.classroom.port.out.ClassroomAppRepository;
import com.io.kira.application.classroom.port.out.ClassroomStudentAppRepository;
import com.io.kira.application.classroom.port.out.ClassroomStudentUserAppPort;
import com.io.kira.application.classroom.result.ClassroomStudentData;
import com.io.kira.common.result.Result;
import com.io.kira.domain.classroom.entity.ClassroomStudent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetClassroomStudentService implements GetClassroomStudentUseCase {

    private final ClassroomStudentAppRepository classroomStudentAppRepository;
    private final ClassroomAppRepository classroomAppRepository;
    private final ClassroomStudentUserAppPort classroomStudentUserAppPort;

    public Result<List<ClassroomStudentData>, SimpleClassroomError> execute(GetClassroomStudentCommand command) {
        if (!classroomAppRepository.existsByClassroomIdAndInstructorUserId(command.classroomId(), command.userId())){
            return Result.fail(SimpleClassroomError.USER_NOT_CLASSROOM_INSTRUCTOR);
        }

        List<ClassroomStudent> classroomStudents = classroomStudentAppRepository.findClassroomStudents(
                command.classroomId(),
                command.status(),
                command.ascending()
        );

        List<ClassroomStudentData> studentDataList = classroomStudentUserAppPort.addUserData(classroomStudents);
        return Result.ok(studentDataList);
    }

}