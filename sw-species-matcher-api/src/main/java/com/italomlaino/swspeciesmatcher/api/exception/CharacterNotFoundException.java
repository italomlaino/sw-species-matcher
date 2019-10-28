package com.italomlaino.swspeciesmatcher.api.exception;

public class CharacterNotFoundException extends RuntimeException {

    public CharacterNotFoundException() {
        super("character-not-found");
    }
}
