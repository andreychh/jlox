package com.andreychh.lox.source;

import com.andreychh.lox.Position;

/**
 * Source represents a stream of characters with position tracking and fragment extraction.
 * <p>
 * This interface provides immutable operations for navigating and extracting content from character streams,
 * maintaining position information for error reporting and debugging.
 * <p>
 * {@snippet :
 * // Basic usage
 * Source input = new TextSource("hello world");
 * String firstChar = input.peek(0);  // "h"
 * String fifthChar = input.peek(4);  // "o"
 *
 * // Fragment extraction
 * Fragment fragment = input.take(5);  // value="hello", remaining=" world"
 * Source remaining = fragment.remaining();
 *
 * // Position tracking
 * Source multiline = new TextSource("line1\nline2");
 * Position pos = multiline.skip(6).position();  // Position(2, 1)
 *}
 */
public interface Source {
    /**
     * Checks if there are at least {@code count} characters remaining in the source.
     *
     * @param count The number of characters to check for.
     * @return {@code true} if there are enough characters, {@code false} otherwise.
     */
    boolean hasNext(int count);

    /**
     * Peeks at the character at the specified offset from the current position.
     *
     * @param offset The offset from the current position.
     * @return The character at the given offset as a String.
     */
    String peek(int offset);

    /**
     * Takes a fragment of the specified length from the source.
     *
     * @param count The number of characters to take.
     * @return A Fragment containing the taken value and the remaining Source.
     */
    Fragment take(int count);

    /**
     * Skips the specified number of characters in the source.
     *
     * @param count The number of characters to skip.
     * @return The Source after skipping the characters.
     */
    Source skip(int count);

    /**
     * Returns the current position in the source.
     *
     * @return The Position object representing the current line and column.
     */
    Position position();
}
