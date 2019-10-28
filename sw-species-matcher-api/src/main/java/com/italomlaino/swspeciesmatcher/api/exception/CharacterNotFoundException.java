package com.italomlaino.swspeciesmatcher.api.exception;

public class CharacterNotFoundException extends RuntimeException {

    public CharacterNotFoundException() {
        super("Character not found");
    }
}
