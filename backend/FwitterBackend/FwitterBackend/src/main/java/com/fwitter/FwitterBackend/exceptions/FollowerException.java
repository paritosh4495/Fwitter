package com.fwitter.FwitterBackend.exceptions;

public class FollowerException extends Exception {
    public static final long serialVersionUID = 1L;

    public FollowerException() {
        super("You Cannot Follow Yourself!");
    }
}
