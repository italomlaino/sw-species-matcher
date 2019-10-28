package com.italomlaino.swspeciesmatcher.api.exception;

public class FailedToFetchCharacterException extends RuntimeException {

    public FailedToFetchCharacterException() {
        super("Failed to fetch the character");
    }
}
