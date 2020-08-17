package com.rrodriguez.glide.bigcorp.exception;

public class InvalidExpansionTypeException extends InvalidExpansionException {
    public InvalidExpansionTypeException(String message) {
        super(new Throwable(message));
    }
}
