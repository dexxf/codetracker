package com.io.kira.domain.classroom.repository;


import java.util.UUID;
public interface ClassroomUserDomainPort {
    boolean existsByUserId(UUID instructorUserId);
}
