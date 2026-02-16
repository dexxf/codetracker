package com.io.codetracker.application.classroom.command;

public record CreateClassroomCommand(
    String name,String description,int maxStudents, 
    Boolean requireApproval,String passcode) {
    }
