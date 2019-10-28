package com.italomlaino.swspeciesmatcher.api.exception;

public class FailedToFetchSpeciesException extends RuntimeException {

    public FailedToFetchSpeciesException() {
        super("Failed to fetch the species");
    }
}
