package com.fwitter.FwitterBackend.exceptions;

public class UserDoesNotExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserDoesNotExistsException() {
        super("The User You Are Looking for Does Not Exists");
    }
}
