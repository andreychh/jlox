package com.andreychh.lox;

import java.util.function.Predicate;

/**
 * Represents the source code being analyzed.
 * <p>
 * This immutable class provides methods to peek at characters, extract
 * substrings, and navigate through the source text without modifying the
 * original content. Each operation returns a new Source instance with an
 * updated offset.
 */
public final class Source {
    private final String text;
    private final int offset;

    /**
     * Creates a new source with the specified text and offset.
     *
     * @param text   The complete source code text
     * @param offset The current position in the text
     */
    public Source(final String text, final int offset) {
        this.text = text;
        this.offset = offset;
    }

    /**
     * Creates a new source with the specified text and an initial offset of 0.
     *
     * @param text The complete source code text
     */
    public Source(final String text) {
        this(text, 0);
    }

    /**
     * Returns the character at the current offset plus the specified offset.
     *
     * @param offset The additional offset relative to the current position
     * @return The character at the specified position
     */
    public char peek(final int offset) {
        return this.text.charAt(this.offset + offset);
    }

    /**
     * Checks if it's possible to peek at the specified offset.
     *
     * @param offset The additional offset relative to the current position
     * @return true if the position is within the bounds of the source text,
     * false otherwise
     */
    public boolean canPeek(final int offset) {
        int position = this.offset + offset;
        return position >= 0 && position < this.text.length();
    }

    /**
     * Extracts a substring of the specified length starting at the current
     * offset.
     *
     * @param count The number of characters to extract
     * @return A substring of the source text
     */
    public String take(final int count) {
        return this.text.substring(this.offset, this.offset + count);
    }

    /**
     * Returns a new Source with the offset advanced by the specified amount.
     *
     * @param offset The number of characters to advance
     * @return A new Source with the updated offset
     */
    public Source skip(final int offset) {
        return new Source(this.text, this.offset + offset);
    }

    /**
     * Checks if it's possible to skip the specified number of characters.
     *
     * @param offset The number of characters to potentially skip
     * @return true if skipping is possible within the bounds of the text, false
     * otherwise
     */
    public boolean canSkip(final int offset) {
        int position = this.offset + offset;
        return position >= 0 && position <= this.text.length();
    }

    /**
     * Calculates the line and column position at the current offset.
     *
     * @return A Position object representing the current location in the source
     * text
     */
    public Position position() {
        int line = 1;
        int column = 1;
        for (int i = 0; i < this.offset; i++) {
            if (this.text.charAt(i) == '\n') {
                line++;
                column = 1;
            } else {
                column++;
            }
        }
        return new Position(line, column);
    }

    /**
     * Returns a new Source with the offset advanced as long as characters
     * satisfy the predicate.
     *
     * @param predicate A function that tests each character
     * @return A new Source with the offset advanced past all matching
     * characters
     */
    public Source skipWhile(final Predicate<Character> predicate) {
        int offset = this.offset;
        while (offset < this.text.length() && predicate.test(this.text.charAt(offset))) {
            offset++;
        }
        return new Source(this.text, offset);
    }

    /**
     * Extracts the substring between this source's offset and another source's
     * offset.
     *
     * @param end The ending source position
     * @return The substring between this source's offset and the end source's
     * offset
     * @throws IllegalArgumentException if the sources reference different texts
     */
    public String sliceUntil(final Source end) {
        if (this.text != end.text) {
            throw new IllegalArgumentException("Cannot slice between different source texts.");
        }
        return this.text.substring(this.offset, end.offset);
    }
}
