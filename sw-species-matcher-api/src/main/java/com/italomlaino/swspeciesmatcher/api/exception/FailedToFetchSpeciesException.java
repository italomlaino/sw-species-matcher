package com.italomlaino.swspeciesmatcher.api.exception;

public class FailedToFetchSpeciesException extends RuntimeException {

    public FailedToFetchSpeciesException() {
        super("failed-to-fetch-the-species");
    }
}
