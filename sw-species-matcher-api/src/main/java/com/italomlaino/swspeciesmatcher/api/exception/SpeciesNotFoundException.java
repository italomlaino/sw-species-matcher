package com.italomlaino.swspeciesmatcher.api.exception;

public class SpeciesNotFoundException extends RuntimeException {

    public SpeciesNotFoundException() {
        super("species-not-found");
    }
}
