package com.io.codetracker.domain.auth.valueobject;

import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.auth.result.EmailResult;

import java.util.regex.Pattern;

public final class Email {

    private final String value;

    private Email(String value) {
        this.value = value;
    }

    public static Result<Email, EmailResult> of(String value) {
        if(value == null || value.isEmpty()) {
            return Result.fail(EmailResult.EMAIL_EMPTY);
        }

        if(!Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$").matcher(value).matches()) {
            return Result.fail(EmailResult.INVALID_EMAIL_FORMAT);
        }

        return Result.ok(new Email(value));
    }

    public String getValue() {
        return value;
    }

}
