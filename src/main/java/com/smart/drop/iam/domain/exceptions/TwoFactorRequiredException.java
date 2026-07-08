package com.smart.drop.iam.domain.exceptions;

public class TwoFactorRequiredException extends RuntimeException {
    public TwoFactorRequiredException(String email) {
        super("Two-Factor Authentication required for email: " + email);
    }
}