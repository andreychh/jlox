package com.andreychh.lox.source;

import com.andreychh.lox.Position;

/**
 * An immutable stream of characters for lexical analysis. Defines the essential contract for consuming a source
 * character by character.
 */
public interface Source {
    /**
     * Looks at the current character without consuming it.
     *
     * @return The character at the current position.
     * @throws IllegalStateException If the source has no more characters, providing context about the position of the
     *                               error.
     */
    char peek();

    /**
     * Checks if there are more characters to consume.
     *
     * @return {@code true} if the source has more characters, {@code false} otherwise.
     */
    boolean hasNext();

    /**
     * Consumes the current character and returns the next state of the source.
     *
     * @return A new {@link Source} instance advanced by one character.
     * @throws IllegalStateException If the source has no more characters.
     */
    Source next();

    /**
     * Returns the current position (line and column) in the source.
     *
     * @return The current {@link Position}.
     */
    Position position();
}
