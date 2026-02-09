package com.io.codetracker.infrastructure.auth.persistence.entity;

import com.io.codetracker.infrastructure.auth.persistence.converter.TokenEncryptionConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "github_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GithubAccountEntity {

    @Id
    @Column(name = "githubAccountId", nullable = false)
    private String githubAccountId;
    
    @Column(name = "auth_id", nullable = false)
    private String authId;

    @Column(name = "github_id", nullable = false)
    private Long githubId;

    @Convert(converter = TokenEncryptionConverter.class)
    @Column(name = "accessToken", nullable = false)
    private String accessToken;

}
