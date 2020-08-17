package com.rrodriguez.glide.bigcorp.exception;

public class InvalidExpansionException extends Exception {
    public InvalidExpansionException(Throwable cause) {
        super("Invalid expansion argument.", cause);
    }
}
