package com.andreychh.lox.error;

import com.andreychh.lox.Position;

/**
 * Represents an error encountered during lexical analysis.
 * <p>
 * This class encapsulates an error message and the position in the source
 * code where the error occurred.
 */
public final class Error {
    private final String message;
    private final Position position;

    /**
     * Creates a new error with the specified message and position.
     *
     * @param message  The error message describing what went wrong
     * @param position The position in the source code where the error occurred
     */
    public Error(final String message, final Position position) {
        this.message = message;
        this.position = position;
    }

    /**
     * Returns a formatted string representation of the error.
     *
     * @return A string containing the error message and position
     */
    public String format() {
        return "Error: %s at %s".formatted(this.message, this.position.format());
    }
}
