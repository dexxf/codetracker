package com.io.kira.infrastructure.auth.persistence.entity;

import com.io.kira.domain.auth.valueobject.Roles;
import com.io.kira.domain.auth.valueobject.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "auth", uniqueConstraints = {
        @UniqueConstraint(name = "uk_auth_username", columnNames = "username")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthEntity {

    @Id
    @Column(name = "auth_id", nullable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @OneToOne(
            mappedBy = "authEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private GithubAccountEntity githubAccountEntity;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Roles role;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }


    public void linkGithubAccount(GithubAccountEntity githubAccountEntity) {
        githubAccountEntity.setAuthEntity(this);
        this.githubAccountEntity = githubAccountEntity;
    }

}

