package com.italomlaino.swspeciesmatcher.api.exception;

public class CharacterIsNotInTheFilmException extends RuntimeException {

    public CharacterIsNotInTheFilmException() {
        super("Character is not in the film");
    }
}
