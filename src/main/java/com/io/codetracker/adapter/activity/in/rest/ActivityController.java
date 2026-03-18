package com.io.codetracker.adapter.activity.in.rest;

import com.io.codetracker.adapter.activity.in.dto.response.GetActivityResponse;
import com.io.codetracker.adapter.activity.in.mapper.AddActivityHttpMapper;
import com.io.codetracker.adapter.activity.in.mapper.EditActivityHttpMapper;
import com.io.codetracker.adapter.activity.in.mapper.GetActivityHttpMapper;
import com.io.codetracker.adapter.activity.in.mapper.RemoveActivityHttpMapper;
import com.io.codetracker.adapter.auth.out.security.AuthPrincipal;
import com.io.codetracker.application.activity.command.AddActivityCommand;
import com.io.codetracker.application.activity.command.EditActivityCommand;
import com.io.codetracker.application.activity.command.GetActivityCommand;
import com.io.codetracker.adapter.activity.in.dto.request.AddActivityRequest;
import com.io.codetracker.adapter.activity.in.dto.request.EditActivityRequest;
import com.io.codetracker.adapter.activity.in.dto.response.ActivityResponse;
import com.io.codetracker.application.activity.error.AddActivityError;
import com.io.codetracker.application.activity.error.EditActivityError;
import com.io.codetracker.application.activity.error.GetActivityError;
import com.io.codetracker.application.activity.error.RemoveActivityError;
import com.io.codetracker.application.activity.port.in.AddActivityUseCase;
import com.io.codetracker.application.activity.port.in.EditActivityUseCase;
import com.io.codetracker.application.activity.port.in.GetActivityUseCase;
import com.io.codetracker.application.activity.port.in.RemoveActivityUseCase;
import com.io.codetracker.application.activity.result.ActivityData;

import com.io.codetracker.common.result.Result;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/classrooms/{classroomId}/activities")
@AllArgsConstructor
public class ActivityController {

    private final AddActivityUseCase addActivityUseCase;
    private final GetActivityUseCase getActivityUseCase;
    private final RemoveActivityUseCase removeActivityUseCase;
    private final EditActivityUseCase editActivityUseCase;

    @PostMapping
        public ResponseEntity<ActivityResponse> addActivity(@PathVariable String classroomId, @Valid @RequestBody AddActivityRequest request, @AuthenticationPrincipal AuthPrincipal principal) {
        AddActivityCommand command = new AddActivityCommand(classroomId, principal.getUserId(), request.title(),
                request.description(), request.dueDate(), request.maxScore(), request.status());
        Result<ActivityData, AddActivityError> response = addActivityUseCase.execute(command);
        return response.success() ? ResponseEntity.status(HttpStatus.CREATED).body(ActivityResponse.success(response.data(), "Successfully added activity"))
                                  : ResponseEntity.status(AddActivityHttpMapper.toStatus(response.error()))
                .body(ActivityResponse.fail(AddActivityHttpMapper.toMessage(response.error())));
    }

    @GetMapping
    public ResponseEntity<GetActivityResponse> getActivities(@PathVariable String classroomId, @AuthenticationPrincipal AuthPrincipal principal) {
            Result<List<ActivityData>, GetActivityError> response =  getActivityUseCase.execute(new GetActivityCommand(classroomId,principal.getUserId()));
            return response.success() ? ResponseEntity.ok(GetActivityResponse.success(response.data()))
                                      : ResponseEntity.status(GetActivityHttpMapper.toStatus(response.error()))
                    .body(GetActivityResponse.fail(GetActivityHttpMapper.toMessage(response.error())));
    }

    @DeleteMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> removeActivity(@PathVariable String classroomId, @PathVariable String activityId, @AuthenticationPrincipal AuthPrincipal authPrincipal) {
        Result<ActivityData, RemoveActivityError> response = removeActivityUseCase.execute(classroomId,activityId,authPrincipal.getUserId());
        return !response.success() ?  ResponseEntity.status(RemoveActivityHttpMapper.toStatus(response.error()))
                .body(ActivityResponse.fail(RemoveActivityHttpMapper.toMessage(response.error())))
                : ResponseEntity.ok(ActivityResponse.success(response.data(), "Successfully Removed Activity"));
    }

    @PutMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> updateActivity(@PathVariable String classroomId, @PathVariable String activityId, @Valid @RequestBody EditActivityRequest request, @AuthenticationPrincipal AuthPrincipal authPrincipal) {
        Result<ActivityData, EditActivityError> response =  editActivityUseCase.execute(new EditActivityCommand(authPrincipal.getUserId(),
                classroomId,activityId,request.title(),request.description(),request.dueDate(),request.status(),request.maxScore()));

        return !response.success() ?  ResponseEntity.status(EditActivityHttpMapper.toStatus(response.error()))
                .body(ActivityResponse.fail(EditActivityHttpMapper.toMessage(response.error())))
                : ResponseEntity.ok(ActivityResponse.success(response.data(), "Successfully Updated Activity"));
        }
}