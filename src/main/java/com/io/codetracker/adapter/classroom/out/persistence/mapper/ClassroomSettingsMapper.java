package com.io.codetracker.adapter.classroom.out.persistence.mapper;

import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomSettingsEntity;
import com.io.codetracker.domain.classroom.entity.ClassroomSettings;

public final class ClassroomSettingsMapper {
    
    private ClassroomSettingsMapper() {}
    
    public static ClassroomSettingsEntity toEntity(ClassroomSettings domain) {
        if (domain == null) {
            throw new IllegalArgumentException("ClassroomSettings domain object cannot be null");
        }
        
        ClassroomSettingsEntity entity = new ClassroomSettingsEntity();
        entity.setRequireApproval(domain.isRequireApproval());
        entity.setPasscode(domain.getPasscode());
        entity.setMaxStudents(domain.getMaxStudents());
        entity.setClassroomId(domain.getClassroomId());
        return entity;
    }
    
   public static ClassroomSettings toDomain(ClassroomSettingsEntity entity) {
    if (entity == null || entity.getClassroom() == null) {
        throw new IllegalArgumentException("ClassroomSettingsEntity or its classroom cannot be null");
    }
    
    return new ClassroomSettings(
        entity.getClassroom().getClassroomId(),
        entity.isRequireApproval(),
        entity.getPasscode(),
        entity.getMaxStudents()
    );
}

    public static void updateEntity(ClassroomSettings domain, ClassroomSettingsEntity entity) {
       if (domain == null || entity == null) {
           throw new IllegalArgumentException("Neither domain nor entity can be null");
       }
       entity.setRequireApproval(domain.isRequireApproval());
       entity.setPasscode(domain.getPasscode());
       entity.setMaxStudents(domain.getMaxStudents());
}   

}