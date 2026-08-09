package com.io.kira.adapter.classroom.in.rest;

import com.io.kira.adapter.auth.out.security.AuthPrincipal;
import com.io.kira.adapter.classroom.in.mapper.SimpleClassroomHttpMapper;
import com.io.kira.application.classroom.command.GetClassroomStudentCommand;
import com.io.kira.application.classroom.error.SimpleClassroomError;
import com.io.kira.application.classroom.port.in.GetClassroomStudentUseCase;
import com.io.kira.application.classroom.result.ClassroomStudentData;
import com.io.kira.common.result.Result;
import com.io.kira.domain.classroom.valueObject.StudentStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/classrooms/{classroomId}/students")
public class ClassroomStudentController {

    private final GetClassroomStudentUseCase getClassroomStudentUseCase;

    @GetMapping
    public ResponseEntity<?> getStudents(@PathVariable UUID classroomId,
                                         @RequestParam(defaultValue = "ACTIVE") StudentStatus status,
                                         @RequestParam(defaultValue = "true") boolean ascending,
                                         @AuthenticationPrincipal AuthPrincipal principal) {
        Result<List<ClassroomStudentData>, SimpleClassroomError> response = getClassroomStudentUseCase.execute(new GetClassroomStudentCommand(principal.getUserId(), classroomId, status, ascending));
        return response.success() ? ResponseEntity.ok(response.data()) : ResponseEntity.status(SimpleClassroomHttpMapper.toStatus(response.error()))
        .body(SimpleClassroomHttpMapper.toMessage(response.error()));
    }

}
