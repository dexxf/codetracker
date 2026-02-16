package com.io.codetracker.application.classroom.result;

public record CreateClassroomData(
    String classroomId,
    String name,
    String description,
    String classCode,
    String status,
    int maxStudents,
    boolean requireApproval
) {



    
}
