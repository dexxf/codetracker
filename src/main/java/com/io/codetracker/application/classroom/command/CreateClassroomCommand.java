package com.io.codetracker.application.classroom.command;

public record CreateClassroomCommand(
    String name,String description,int maxStudents, 
    boolean requireApproval,String passcode) {
    }
