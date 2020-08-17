package com.rrodriguez.glide.bigcorp.exception;

public class InvalidExpansionDependencyException extends InvalidExpansionException {
    public InvalidExpansionDependencyException(String message) {
        super(new Throwable(message));
    }
}
