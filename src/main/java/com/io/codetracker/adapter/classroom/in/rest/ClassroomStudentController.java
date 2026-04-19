package com.io.codetracker.adapter.classroom.in.rest;

import com.io.codetracker.adapter.auth.out.security.AuthPrincipal;
import com.io.codetracker.adapter.classroom.in.mapper.SimpleClassroomHttpMapper;
import com.io.codetracker.application.classroom.command.GetClassroomStudentCommand;
import com.io.codetracker.application.classroom.error.SimpleClassroomError;
import com.io.codetracker.application.classroom.port.in.GetClassroomStudentUseCase;
import com.io.codetracker.application.classroom.result.ClassroomStudentData;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.classroom.valueObject.StudentStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/classrooms/{classroomId}/students")
public class ClassroomStudentController {

    private final GetClassroomStudentUseCase getClassroomStudentUseCase;

    @GetMapping
    public ResponseEntity<?> getStudents(@PathVariable String classroomId,
                                         @RequestParam(defaultValue = "ACTIVE") StudentStatus status,
                                         @RequestParam(defaultValue = "true") boolean ascending,
                                         @AuthenticationPrincipal AuthPrincipal principal) {
        Result<List<ClassroomStudentData>, SimpleClassroomError> response = getClassroomStudentUseCase.execute(new GetClassroomStudentCommand(principal.getUserId(), classroomId, status, ascending));
        return response.success() ? ResponseEntity.ok(response.data()) : ResponseEntity.status(SimpleClassroomHttpMapper.toStatus(response.error()))
        .body(SimpleClassroomHttpMapper.toMessage(response.error()));
    }

}
