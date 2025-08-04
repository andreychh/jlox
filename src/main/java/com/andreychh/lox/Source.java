package com.andreychh.lox;

import java.util.function.Predicate;

/**
 * Represents the source code being analyzed.
 * <p>
 * This immutable class provides methods to examine characters (peek),
 * extract substrings (take), and navigate through the source text (skip)
 * without modifying the original content. Each operation follows the pattern:
 * - Basic operation: peek/take/skip
 * - Bounds checking: canPeek/canTake/canSkip
 * - Predicate-based variation: takeWhile/skipWhile
 * <p>
 * Each navigation operation returns a new Source instance with an updated
 * offset.
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
     * @throws IllegalArgumentException  if offset is negative
     * @throws IndexOutOfBoundsException if the position is out of bounds
     */
    public char peek(final int offset) {
        if (offset < 0) {
            throw new IllegalArgumentException(
                    "Negative offset is not allowed: %d".formatted(offset)
            );
        }
        if (!this.canPeek(offset)) {
            throw new IndexOutOfBoundsException(
                    "Cannot peek at relative offset: %d (absolute position: %s)"
                            .formatted(offset, this.position().format())
            );
        }
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
     * @throws IllegalArgumentException  if count is negative
     * @throws IndexOutOfBoundsException if the requested range is out of bounds
     */
    public String take(final int count) {
        if (count < 0) {
            throw new IllegalArgumentException(
                    "Cannot take a negative number of characters: %d".formatted(count)
            );
        }
        if (!this.canTake(count)) {
            throw new IndexOutOfBoundsException(
                    "Cannot take %d characters from position %s".formatted(count, this.position().format())
            );
        }
        return this.text.substring(this.offset, this.offset + count);
    }

    /**
     * Extracts a substring starting at the current offset and continuing as
     * long as characters satisfy the predicate.
     *
     * @param predicate A function that tests each character
     * @return A substring of consecutive characters that satisfy the predicate
     */
    public String takeWhile(final Predicate<Character> predicate) {
        int end = this.offset;
        while (end < this.text.length() && predicate.test(this.text.charAt(end))) {
            end++;
        }
        return this.text.substring(this.offset, end);
    }

    /**
     * Checks if it's possible to take the specified number of characters.
     *
     * @param count The number of characters to potentially take
     * @return true if taking is possible within the bounds of the text,
     * false otherwise
     */
    public boolean canTake(final int count) {
        int offset = this.offset + count;
        return offset >= 0 && offset <= this.text.length();
    }

    /**
     * Returns a new Source with the offset advanced by the specified amount.
     *
     * @param offset The number of characters to advance
     * @return A new Source with the updated offset
     * @throws IllegalArgumentException  if offset is negative
     * @throws IndexOutOfBoundsException if the new position would be out of
     * bounds
     */
    public Source skip(final int offset) {
        if (offset < 0) {
            throw new IllegalArgumentException(
                    "Negative skip offset is not allowed: %d".formatted(offset)
            );
        }
        if (!this.canSkip(offset)) {
            throw new IndexOutOfBoundsException(
                    "Cannot skip by %d from position %s".formatted(offset, this.position())
            );
        }
        return new Source(this.text, this.offset + offset);
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
}
