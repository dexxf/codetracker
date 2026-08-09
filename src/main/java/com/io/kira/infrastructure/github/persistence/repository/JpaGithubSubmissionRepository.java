package com.io.kira.infrastructure.github.persistence.repository;

import com.io.kira.application.classroom.result.ClassroomRepositorySubmissionData;
import com.io.kira.infrastructure.github.persistence.entity.GithubSubmissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface JpaGithubSubmissionRepository extends JpaRepository<GithubSubmissionEntity, Long> {

		@Query("""
						SELECT new com.io.kira.application.classroom.result.ClassroomRepositorySubmissionData(
								u.userId,
								u.firstName,
								u.lastName,
								u.profileUrl,
								a.activityId,
								a.title,
								gs.repositoryName,
								gs.repositoryUrl,
								gs.submittedAt
						)
						FROM GithubSubmissionEntity gs
						JOIN gs.studentActivity sa
						JOIN sa.userEntity u
						JOIN sa.activityEntity a
						WHERE a.classroomEntity.classroomId = :classroomId
							AND gs.submittedAt IS NOT NULL
						ORDER BY gs.submittedAt DESC
						""")
		List<ClassroomRepositorySubmissionData> findRecentRepositorySubmissionsByClassroomId(@Param("classroomId") UUID classroomId, Pageable pageable);
}
