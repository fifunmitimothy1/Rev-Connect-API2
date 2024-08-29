package com.rev_connect_api.exceptions;

public class InvalidProfileException extends Exception {
    private String field;

    public InvalidProfileException(String field, String message){
        super(message);
        this.field = field;
    }
    
    public String getField() {
        return this.field;
    }
}
