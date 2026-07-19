package com.io.codetracker.infrastructure.auth.persistence.entity;

import com.io.codetracker.common.util.AESEncryptionConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity
@Table(name = "github_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GithubAccountEntity {

    @Id
    @Column(name = "auth_id",nullable = false)
    private UUID id;

    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name = "auth_id", nullable = false)
    private AuthEntity authEntity;

    @Column(name = "github_id", nullable = false)
    private Long githubId;

    @Convert(converter = AESEncryptionConverter.class)
    @Column(name = "accessToken", nullable = false)
    private String accessToken;

}
