package com.io.codetracker.adapter.activity.in.rest;

import com.io.codetracker.adapter.activity.in.dto.response.GetActivityResponse;
import com.io.codetracker.adapter.auth.out.security.AuthPrincipal;
import com.io.codetracker.application.activity.command.AddActivityCommand;
import com.io.codetracker.application.activity.command.EditActivityCommand;
import com.io.codetracker.application.activity.command.GetActivityCommand;
import com.io.codetracker.adapter.activity.in.dto.request.AddActivityRequest;
import com.io.codetracker.adapter.activity.in.dto.request.EditActivityRequest;
import com.io.codetracker.adapter.activity.in.dto.response.ActivityResponse;
import com.io.codetracker.application.activity.port.in.AddActivityUseCase;
import com.io.codetracker.application.activity.port.in.EditActivityUseCase;
import com.io.codetracker.application.activity.port.in.GetActivityUseCase;
import com.io.codetracker.application.activity.result.ActivityData;
import com.io.codetracker.application.activity.service.RemoveActivityService;

import com.io.codetracker.common.result.Result;
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
    private final RemoveActivityService removeActivityService;
    private final EditActivityUseCase editActivityUseCase;

    @PostMapping
    public ResponseEntity<ActivityResponse> addActivity(@PathVariable String classroomId, @RequestBody AddActivityRequest request, @AuthenticationPrincipal AuthPrincipal principal) {
        AddActivityCommand command = new AddActivityCommand(classroomId, principal.getUserId(), request.title(),
                request.description(), request.dueDate(), request.maxScore(), request.status());

        Result<ActivityData,String> response = addActivityUseCase.execute(command);

        return response.success() ? ResponseEntity.status(HttpStatus.CREATED).body(ActivityResponse.success(response.data(), "Successfully added activity"))
                                  : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ActivityResponse.fail(response.error()));
    }

    @GetMapping
    public ResponseEntity<GetActivityResponse> getActivities(@PathVariable String classroomId, @AuthenticationPrincipal AuthPrincipal principal) {
            Result<List<ActivityData>, String> response =  getActivityUseCase.execute(new GetActivityCommand(classroomId,principal.getUserId()));

            return response.success() ? ResponseEntity.ok().body(GetActivityResponse.success(response.data()))
                                      : ResponseEntity.badRequest().body(GetActivityResponse.fail(response.error()));
    }

    @DeleteMapping("/{activityId}")
    public ResponseEntity<?> removeActivity(@PathVariable String classroomId, @PathVariable String activityId, @AuthenticationPrincipal AuthPrincipal authPrincipal) {
        ActivityResponse response = removeActivityService.execute(classroomId,activityId,authPrincipal.getUserId());
        return !response.success() ?  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response.message())
                : ResponseEntity.ok(response);
    }

    @PutMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> updateActivity(@PathVariable String classroomId, @PathVariable String activityId,@RequestBody EditActivityRequest request, @AuthenticationPrincipal AuthPrincipal authPrincipal) {
        Result<ActivityData, String> response=  editActivityUseCase.execute(new EditActivityCommand(authPrincipal.getUserId(),
                classroomId,activityId,request.title(),request.description(),request.dueDate(),request.status(),request.maxScore()));
        return !response.success() ?  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ActivityResponse.fail(response.error()))
                : ResponseEntity.ok(ActivityResponse.success(response.data(), "Successfully Updated Activity"));
        }
}