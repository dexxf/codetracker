package com.io.codetracker.application.auth.error;

import com.io.codetracker.domain.auth.result.GithubAccountCreationResult;

/*
* Only used so that domain layer GithubAccountCreationResult 
* should not leak outside the application boundary 
* (i made it only so that i can not expose domain layer stuff to my adapter layer)
*/

public enum GithubAccountRegistrationError {
    GITHUB_ID_NOT_FOUND,
    ACCESS_TOKEN_MISSING,
    ALREADY_LINKED;

    public static GithubAccountRegistrationError from(GithubAccountCreationResult error) {
        return GithubAccountRegistrationError.valueOf(error.name());
    }
    
}
