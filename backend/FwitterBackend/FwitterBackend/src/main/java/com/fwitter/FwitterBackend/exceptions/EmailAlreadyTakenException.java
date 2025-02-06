package com.fwitter.FwitterBackend.exceptions;

public class EmailAlreadyTakenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmailAlreadyTakenException() {
        super("The Email Provided is already taken");
    }
}
