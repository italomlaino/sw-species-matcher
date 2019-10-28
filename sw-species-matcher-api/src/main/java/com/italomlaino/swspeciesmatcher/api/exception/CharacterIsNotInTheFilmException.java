package com.italomlaino.swspeciesmatcher.api.exception;

public class CharacterIsNotInTheFilmException extends RuntimeException {

    public CharacterIsNotInTheFilmException() {
        super("character-is-not-in-the-film");
    }
}
