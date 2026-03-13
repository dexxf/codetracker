package com.io.codetracker.adapter.classroom.in.rest;

import com.io.codetracker.adapter.classroom.in.dto.request.JoinClassroomRequest;
import com.io.codetracker.adapter.classroom.in.dto.response.GetClassroomsResponse;
import com.io.codetracker.application.classroom.command.JoinClassroomCommand;
import com.io.codetracker.application.classroom.port.in.CreateClassroomUseCase;
import com.io.codetracker.application.classroom.port.in.GetClassroomStatsUseCase;
import com.io.codetracker.application.classroom.port.in.GetClassroomUseCase;
import com.io.codetracker.application.classroom.result.ClassroomStats;
import com.io.codetracker.application.classroom.result.CreateClassroomData;
import com.io.codetracker.application.classroom.result.GetClassroomsProfessorData;
import com.io.codetracker.application.classroom.service.GetJoinClassroomService;
import com.io.codetracker.application.classroom.service.JoinClassroomService;
import com.io.codetracker.common.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.io.codetracker.adapter.auth.out.security.AuthPrincipal;
import com.io.codetracker.adapter.classroom.in.dto.request.CreateClassroomRequest;
import com.io.codetracker.application.classroom.command.ClassroomStatsCommand;
import com.io.codetracker.application.classroom.command.CreateClassroomCommand;
import com.io.codetracker.adapter.classroom.in.dto.response.CreateClassroomResponse;
import com.io.codetracker.adapter.classroom.in.dto.response.GetClassroomStatsResponse;
import com.io.codetracker.application.classroom.service.GetClassroomStatsService;
import com.io.codetracker.common.response.ErrorResponse;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
@AllArgsConstructor
public class ClassroomController {
    
    private final CreateClassroomUseCase createClassroomUseCase;
    private final GetClassroomUseCase getClassroomsUseCase;
    private final JoinClassroomService joinClassroomService;
    private final GetJoinClassroomService getJoinClassroomService;
    private final GetClassroomStatsUseCase getClassroomStatsUseCase;
    
@PostMapping("/create")
public ResponseEntity<CreateClassroomResponse> createClassroom(@AuthenticationPrincipal AuthPrincipal authPrincipal,@Valid @RequestBody CreateClassroomRequest request) {
    Result<CreateClassroomData, String> result =
        createClassroomUseCase.execute(authPrincipal.getUserId(), new CreateClassroomCommand(
            request.name(),
            request.description(),
            request.maxStudents(),
            request.requireApproval(),
            request.passcode()
        ));

    if (!result.success()) {
        return ResponseEntity
            .badRequest()
            .body(CreateClassroomResponse.fail(result.error()));
    }
    
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(CreateClassroomResponse.ok(result.data()));
}

    @GetMapping("/me")
    public ResponseEntity<GetClassroomsResponse> getClassrooms(@AuthenticationPrincipal AuthPrincipal authPrincipal) {
        Result<List<GetClassroomsProfessorData>, String> result = getClassroomsUseCase.execute(authPrincipal.getUserId());
        if(!result.success()) {
            return ResponseEntity.badRequest().body(GetClassroomsResponse.fail(result.error()));
        }
        return ResponseEntity.ok(GetClassroomsResponse.ok(result.data()));
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinClassroom(@AuthenticationPrincipal AuthPrincipal authPrincipal, @Valid @RequestBody JoinClassroomRequest request) {
        var response = joinClassroomService.execute(
                new JoinClassroomCommand(authPrincipal.getUserId(), request.code(), request.passcode()));

        if (!response.success()) {
            return ResponseEntity.badRequest().body(ErrorResponse.of(response.error(), 400));
        }

        return ResponseEntity.ok(response.data());
    }

    @GetMapping("/join")
    public ResponseEntity<?> getJoinedClassrooms(
            @AuthenticationPrincipal AuthPrincipal authPrincipal) {
        var result = getJoinClassroomService.execute(authPrincipal.getUserId());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{classroomId}/stats")
    public ResponseEntity<GetClassroomStatsResponse> getClassroomStats(
            @AuthenticationPrincipal AuthPrincipal authPrincipal,
            @PathVariable String classroomId) {
        Result<ClassroomStats, String> result = getClassroomStatsUseCase.execute(new ClassroomStatsCommand(classroomId, authPrincipal.getUserId()));
        if (!result.success()) {
            return ResponseEntity.badRequest().body(GetClassroomStatsResponse.fail(result.error()));
        }
        return ResponseEntity.ok(GetClassroomStatsResponse.success(result.data()));
    }

}