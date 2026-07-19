package com.io.codetracker.application.classroom.command;


import java.util.UUID;
public record ClassroomStatsCommand (String classroomId, UUID userId){
    
}

