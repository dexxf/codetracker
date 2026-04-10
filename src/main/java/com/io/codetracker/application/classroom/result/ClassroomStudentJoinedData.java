package com.io.codetracker.application.classroom.result;

import java.time.LocalDateTime;

public record ClassroomStudentJoinedData(
        String studentUserId,
        String firstName,
        String lastName,
        String profileUrl,
        LocalDateTime joinedAt
) {
}
