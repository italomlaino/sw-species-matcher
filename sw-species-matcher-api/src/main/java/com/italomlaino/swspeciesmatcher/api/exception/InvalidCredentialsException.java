package com.italomlaino.swspeciesmatcher.api.exception;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("invalid-credentials");
    }
}
