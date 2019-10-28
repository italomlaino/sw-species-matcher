package com.italomlaino.swspeciesmatcher.api.dto;

public class ErrorDTO implements ResponseDTO {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
