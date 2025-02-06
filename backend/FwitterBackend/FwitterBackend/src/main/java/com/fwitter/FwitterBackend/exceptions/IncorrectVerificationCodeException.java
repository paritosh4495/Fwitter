package com.fwitter.FwitterBackend.exceptions;

public class IncorrectVerificationCodeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public IncorrectVerificationCodeException() {
        super("Verification code is incorrect., please try again.");
    }
}
