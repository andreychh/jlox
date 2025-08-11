package com.andreychh.lox.source;

/**
 * Provides advanced operations on a {@link Source}, such as checking if the source matches patterns, taking
 * fragments by pattern, and skipping by pattern.
 * <p>
 * {@snippet :
 * Source src = new TextSource("abc123");
 * PatternSource ps = new PatternSource(src);
 * boolean matches = ps.matches(new String[]{"[a-z]", "[a-z]", "[a-z]"}); // true
 * Fragment fragment = ps.take("[a-z]"); // value="abc", remaining="123"
 * Source skipped = ps.skip("[0-9]"); // remaining="abc123"
 *}
 *
 * @apiNote Each {@code pattern} must be a regular expression that matches exactly one character. For example,
 * {@code "[a-z]"} is allowed, but {@code "ab"} or {@code "[a-z]+"} are not. The {@code patterns} array must contain
 * only such single-character regular expressions.
 */
public final class PatternSource {
    private final Source origin;

    /**
     * Constructs a PatternSource wrapping the given Source.
     *
     * @param origin the underlying Source to operate on
     */
    public PatternSource(final Source origin) {
        this.origin = origin;
    }

    /**
     * Checks if the next characters in the source match the given array of regular expression patterns.
     *
     * @param patterns array of regular expression patterns that each match exactly one character
     * @return {@code true} if all patterns match the corresponding characters, {@code false} otherwise
     */
    public boolean matches(final String[] patterns) {
        if (!this.origin.hasNext(patterns.length)) {
            return false;
        }
        for (int i = 0; i < patterns.length; i++) {
            if (!this.origin.peek(i).matches(patterns[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Takes a fragment from the source as long as the characters match the given regular expression pattern.
     *
     * @param pattern the regular expression pattern that matches exactly one character
     * @return a Fragment containing the matched value and the remaining Source
     */
    public Fragment take(final String pattern) {
        return this.origin.take(this.count(pattern));
    }

    /**
     * Skips characters in the source as long as they match the given regular expression pattern.
     *
     * @param pattern the regular expression pattern to match characters
     * @return the Source after skipping the matched characters
     */
    public Source skip(final String pattern) {
        return this.origin.skip(this.count(pattern));
    }

    /**
     * Counts how many consecutive characters from the current position match the given regular expression pattern.
     *
     * @param pattern the regular expression pattern to match characters
     * @return the number of matching characters
     */
    private int count(final String pattern) {
        int count = 0;
        while (this.origin.hasNext(count + 1) && this.origin.peek(count).matches(pattern)) {
            count++;
        }
        return count;
    }
}
