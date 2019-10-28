package com.italomlaino.swspeciesmatcher.api.exception;

public class FailedToFetchCharacterException extends RuntimeException {

    public FailedToFetchCharacterException() {
        super("failed-to-fetch-the-character");
    }
}
