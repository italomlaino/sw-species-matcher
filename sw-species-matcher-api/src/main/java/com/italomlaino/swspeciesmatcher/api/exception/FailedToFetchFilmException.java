package com.italomlaino.swspeciesmatcher.api.exception;

public class FailedToFetchFilmException extends RuntimeException {

    public FailedToFetchFilmException() {
        super("Failed to fetch the film");
    }
}
