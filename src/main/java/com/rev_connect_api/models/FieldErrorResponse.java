package com.rev_connect_api.models;

import java.util.Objects;

public class FieldErrorResponse {
    private String field;
    private String message;

    public FieldErrorResponse() {

    }
    
    public FieldErrorResponse(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField(){
        return this.field;
    }

    public String getMessage(){
        return this.message;
    }

    @Override
    public String toString(){
        return "{ field: " + this.field + ", message: " + this.message + " }";
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || o.getClass() != this.getClass()) {
            return false;
        }

        FieldErrorResponse otherResponse = (FieldErrorResponse) o;
        return Objects.equals(this.field, otherResponse.getField()) && Objects.equals(this.message, otherResponse.getMessage());
    }
}
