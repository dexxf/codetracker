package com.io.codetracker.application.activity.service;

import com.io.codetracker.application.activity.error.MarkStudentAsGradedError;
import com.io.codetracker.application.activity.port.in.MarkStudentAsGradedUseCase;
import com.io.codetracker.application.activity.port.out.ActivityClassroomAppPort;
import com.io.codetracker.application.activity.port.out.StudentActivityAppRepository;
import com.io.codetracker.application.activity.result.StudentActivitySubmissionData;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.activity.entity.StudentActivity;
import com.io.codetracker.domain.activity.valueObject.SubmissionStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MarkStudentAsGradedService implements MarkStudentAsGradedUseCase {

    private final StudentActivityAppRepository studentActivityAppRepository;
    private final ActivityClassroomAppPort activityClassroomAppPort;

    @Override
    public Result<StudentActivitySubmissionData, MarkStudentAsGradedError> grade(UUID instructorUserId, UUID classroomId, String activityId, UUID studentUserId, String feedback, Integer score) {
        if (!activityClassroomAppPort.existsByClassroomId(classroomId))
            return Result.fail(MarkStudentAsGradedError.CLASSROOM_NOT_FOUND);

        if (!activityClassroomAppPort.existsByClassroomIdAndInstructorUserId(classroomId, instructorUserId))
            return Result.fail(MarkStudentAsGradedError.USER_NOT_CLASSROOM_INSTRUCTOR);

        if (!activityClassroomAppPort.existsByClassroomIdAndActivityId(classroomId, activityId))
            return Result.fail(MarkStudentAsGradedError.ACTIVITY_NOT_FOUND);

        Integer activityMaxScore = activityClassroomAppPort
                .findMaxScoreByClassroomIdAndActivityId(classroomId, activityId)
                .orElse(null);
        if (activityMaxScore != null && score != null && score > activityMaxScore)
            return Result.fail(MarkStudentAsGradedError.SCORE_EXCEEDS_MAX_SCORE);

        if (!studentActivityAppRepository.existsByUserId(studentUserId))
            return Result.fail(MarkStudentAsGradedError.STUDENT_NOT_FOUND);

        if (!activityClassroomAppPort.existsByClassroomIdAndStudentUserId(classroomId, studentUserId))
            return Result.fail(MarkStudentAsGradedError.STUDENT_NOT_CLASSROOM_STUDENT);

        Optional<StudentActivity> studentActivityOptional = studentActivityAppRepository.findByUserIdAndActivityId(studentUserId, activityId);
        if (studentActivityOptional.isEmpty())
            return Result.fail(MarkStudentAsGradedError.REPOSITORY_SUBMISSION_NOT_FOUND);

        StudentActivity studentActivity = studentActivityOptional.get();

        if (studentActivity.getSubmissionStatus() == SubmissionStatus.GRADED)
            return Result.fail(MarkStudentAsGradedError.ALREADY_GRADED);

        if (studentActivity.getSubmissionStatus() != SubmissionStatus.SUBMITTED)
            return Result.fail(MarkStudentAsGradedError.ACTIVITY_NOT_SUBMITTED);

        try {
            studentActivity.grade(feedback, score);
        } catch (IllegalArgumentException e) {
            return Result.fail(MarkStudentAsGradedError.INVALID_SCORE);
        }

        try {
            StudentActivity savedStudentActivity = studentActivityAppRepository.save(studentActivity);
            return Result.ok(StudentActivitySubmissionData.from(savedStudentActivity));
        } catch (RuntimeException e) {
            return Result.fail(MarkStudentAsGradedError.SAVE_FAILED);
        }
    }
}
