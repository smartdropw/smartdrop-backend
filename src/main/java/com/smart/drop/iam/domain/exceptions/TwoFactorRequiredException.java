package com.smart.drop.iam.domain.exceptions;

public class TwoFactorRequiredException extends RuntimeException {
    private final String code;

    public TwoFactorRequiredException(String email, String code) {
        super("Two-Factor Authentication required for email: " + email);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}