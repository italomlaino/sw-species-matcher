package com.italomlaino.swspeciesmatcher.api.exception;

public class FilmNotFoundException extends RuntimeException {

    public FilmNotFoundException() {
        super("film-not-found");
    }
}
