package com.io.codetracker.adapter.classroom.in.rest;

import com.io.codetracker.adapter.classroom.in.dto.request.JoinClassroomRequest;
import com.io.codetracker.adapter.classroom.in.dto.request.EditClassroomRequest;
import com.io.codetracker.adapter.classroom.in.dto.response.ClassroomJoinResponse;
import com.io.codetracker.adapter.classroom.in.dto.response.EditClassroomResponse;
import com.io.codetracker.adapter.classroom.in.dto.response.GetClassroomsResponse;
import com.io.codetracker.adapter.classroom.in.mapper.ClassroomJoinHttpMapper;
import com.io.codetracker.adapter.classroom.in.mapper.CreateClassroomHttpMapper;
import com.io.codetracker.adapter.classroom.in.mapper.EditClassroomHttpMapper;
import com.io.codetracker.adapter.classroom.in.mapper.SimpleClassroomHttpMapper;
import com.io.codetracker.application.classroom.command.EditClassroomCommand;
import com.io.codetracker.application.classroom.command.JoinClassroomCommand;
import com.io.codetracker.application.classroom.error.ClassroomJoinError;
import com.io.codetracker.application.classroom.error.CreateClassroomError;
import com.io.codetracker.application.classroom.error.EditClassroomError;
import com.io.codetracker.application.classroom.error.SimpleClassroomError;
import com.io.codetracker.application.classroom.port.in.*;
import com.io.codetracker.application.classroom.result.*;
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

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
@AllArgsConstructor
public class ClassroomController {
    
    private final CreateClassroomUseCase createClassroomUseCase;
    private final GetClassroomUseCase getClassroomsUseCase;
    private final JoinClassroomUseCase joinClassroomUseCase;
    private final GetJoinClassroomUseCase getJoinClassroomUseCase;
    private final GetClassroomStatsUseCase getClassroomStatsUseCase;
    private final EditClassroomUseCase editClassroomUseCase;
    
@PostMapping("/create")
public ResponseEntity<CreateClassroomResponse> createClassroom(@AuthenticationPrincipal AuthPrincipal authPrincipal,@Valid @RequestBody CreateClassroomRequest request) {
    Result<CreateClassroomData, CreateClassroomError> result =
        createClassroomUseCase.execute(authPrincipal.getUserId(), new CreateClassroomCommand(
            request.name(),
            request.description(),
            request.maxStudents(),
            request.requireApproval(),
            request.passcode()
        ));

    if (!result.success()) {
        return ResponseEntity
            .status(CreateClassroomHttpMapper.toStatus(result.error()))
            .body(CreateClassroomResponse.fail(CreateClassroomHttpMapper.toMessage(result.error())));
    }
    
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(CreateClassroomResponse.ok(result.data()));
}

    @GetMapping("/me")
    public ResponseEntity<GetClassroomsResponse> getClassrooms(@AuthenticationPrincipal AuthPrincipal authPrincipal) {
        Result<List<GetClassroomsProfessorData>, SimpleClassroomError> result = getClassroomsUseCase.execute(authPrincipal.getUserId());
        if(!result.success()) {
            return ResponseEntity.status(SimpleClassroomHttpMapper.toStatus(result.error()))
            .body(GetClassroomsResponse.fail(SimpleClassroomHttpMapper.toMessage(result.error())));
        }
        return ResponseEntity.ok(GetClassroomsResponse.ok(result.data()));
    }

    @PostMapping("/join")
    public ResponseEntity<ClassroomJoinResponse> joinClassroom(@AuthenticationPrincipal AuthPrincipal authPrincipal, @Valid @RequestBody JoinClassroomRequest request) {
        Result<ClassroomJoinResult, ClassroomJoinError> response = joinClassroomUseCase.execute(
                new JoinClassroomCommand(authPrincipal.getUserId(), request.code(), request.passcode()));
        if (!response.success()) {
            return ResponseEntity.status(ClassroomJoinHttpMapper.toStatus(response.error()))
            .body(ClassroomJoinResponse.fail(ClassroomJoinHttpMapper.toMessage(response.error())));
        }
        
        return ResponseEntity.ok(ClassroomJoinResponse.ok(response.data()));
    }

    @GetMapping("/join")
    public ResponseEntity<List<GetJoinClassroomDataResult>> getJoinedClassrooms(
            @AuthenticationPrincipal AuthPrincipal authPrincipal) {
        List<GetJoinClassroomDataResult> result = getJoinClassroomUseCase.execute(authPrincipal.getUserId());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{classroomId}/stats")
    public ResponseEntity<GetClassroomStatsResponse> getClassroomStats(
            @AuthenticationPrincipal AuthPrincipal authPrincipal,
            @PathVariable String classroomId) {
        Result<ClassroomStats, SimpleClassroomError> result = getClassroomStatsUseCase.execute(new ClassroomStatsCommand(classroomId, authPrincipal.getUserId()));
        if (!result.success()) {
            return ResponseEntity.status(SimpleClassroomHttpMapper.toStatus(result.error()))
            .body(GetClassroomStatsResponse.fail(SimpleClassroomHttpMapper.toMessage(result.error())));
        }
        return ResponseEntity.ok(GetClassroomStatsResponse.success(result.data()));
    }

    @PutMapping("/{classroomId}")
    public ResponseEntity<EditClassroomResponse> updateClassroom(
            @AuthenticationPrincipal AuthPrincipal authPrincipal,
            @PathVariable String classroomId,
            @Valid @RequestBody EditClassroomRequest request) {
        Result<ClassroomData, EditClassroomError> result = editClassroomUseCase.execute(
                new EditClassroomCommand(
                        authPrincipal.getUserId(),
                        classroomId,
                        request.name(),
                        request.description(),
                    request.maxStudents()
                ));

        if (!result.success()) {
            return ResponseEntity.status(EditClassroomHttpMapper.toStatus(result.error()))
                    .body(EditClassroomResponse.fail(EditClassroomHttpMapper.toMessage(result.error())));
        }

        return ResponseEntity.ok(EditClassroomResponse.ok(result.data()));
    }

}