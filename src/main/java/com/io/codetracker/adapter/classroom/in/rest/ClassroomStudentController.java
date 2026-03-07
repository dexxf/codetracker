package com.io.codetracker.adapter.classroom.in.rest;

import com.io.codetracker.adapter.auth.out.security.AuthPrincipal;
import com.io.codetracker.application.classroom.command.GetClassroomStudentCommand;
import com.io.codetracker.application.classroom.port.in.response.GetClassroomStudentResponse;
import com.io.codetracker.application.classroom.service.GetClassroomStudentService;
import com.io.codetracker.domain.classroom.valueObject.StudentStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/classrooms/{classroomId}/students")
public class ClassroomStudentController {

    private final GetClassroomStudentService getClassroomStudentService;

    @GetMapping
    public ResponseEntity<?> getStudents(@PathVariable String classroomId,
                                         @RequestParam(defaultValue = "ACTIVE") StudentStatus status,
                                         @RequestParam(defaultValue = "true") boolean ascending,
                                         @AuthenticationPrincipal AuthPrincipal principal) {
        GetClassroomStudentResponse response = getClassroomStudentService.execute(
            new GetClassroomStudentCommand(principal.getUserId(), classroomId, status, ascending));
        return response.success() ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body(response.message());
    }

}
