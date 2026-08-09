package com.io.kira.application.classroom.command;

public record CreateClassroomCommand(
    String name,String description,int maxStudents, 
    boolean requireApproval,String passcode) {
    }
