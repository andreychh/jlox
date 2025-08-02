package com.andreychh.lox;

public final class Error {
    private final String message;
    private final Position position;

    public Error(final String message, final Position position) {
        this.message = message;
        this.position = position;
    }

    public String format() {
        return "Error: %s at %s".formatted(this.message, this.position.format());
    }
}
