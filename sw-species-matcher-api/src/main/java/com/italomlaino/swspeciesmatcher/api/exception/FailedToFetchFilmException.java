package com.italomlaino.swspeciesmatcher.api.exception;

public class FailedToFetchFilmException extends RuntimeException {

    public FailedToFetchFilmException() {
        super("failed-to-fetch-the-film");
    }
}
