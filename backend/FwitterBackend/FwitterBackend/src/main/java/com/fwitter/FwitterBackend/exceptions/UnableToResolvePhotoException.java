package com.fwitter.FwitterBackend.exceptions;

public class UnableToResolvePhotoException extends Exception{

    private static final long serialVersionUID = 1L;

    public UnableToResolvePhotoException() {
        super("The Photo You Are Looking For Cannot Be Found!");
    }
}
