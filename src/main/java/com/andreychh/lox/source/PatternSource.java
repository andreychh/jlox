package com.andreychh.lox.source;

/**
 * PatternSource provides advanced operations on a {@link Source}, such as checking conformity to patterns, taking
 * fragments by pattern, and skipping by pattern.
 * <p>
 * {@snippet :
 * Source src = new TextSource("abc123");
 * PatternSource ps = new PatternSource(src);
 * boolean matches = ps.conforms(new String[]{"[a-z]", "[a-z]", "[a-z]"}); // true
 * Fragment fragment = ps.take("[a-z]"); // value="abc", remaining="123"
 * Source skipped = ps.skip("[0-9]"); // remaining="abc123"
 *}
 */
public final class PatternSource {
    private final Source origin;

    /**
     * Constructs a PatternSource wrapping the given Source.
     *
     * @param origin The underlying Source to operate on
     */
    public PatternSource(final Source origin) {
        this.origin = origin;
    }

    /**
     * Checks if the next characters in the source conform to the given array of regex patterns.
     *
     * @param patterns Array of regex patterns to check against the next characters
     * @return true if all patterns match the corresponding characters, false otherwise
     */
    public boolean conforms(final String[] patterns) {
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
     * Takes a fragment from the source as long as the characters match the given regex pattern.
     *
     * @param pattern The regex pattern to match characters
     * @return A Fragment containing the matched value and the remaining Source
     */
    public Fragment take(final String pattern) {
        return this.origin.take(this.count(pattern));
    }

    /**
     * Skips characters in the source as long as they match the given regex pattern.
     *
     * @param pattern The regex pattern to match characters
     * @return The Source after skipping the matched characters
     */
    public Source skip(final String pattern) {
        return this.origin.skip(this.count(pattern));
    }

    /**
     * Counts how many consecutive characters from the current position match the given regex pattern.
     *
     * @param pattern The regex pattern to match characters
     * @return The number of matching characters
     */
    private int count(final String pattern) {
        int count = 0;
        while (this.origin.hasNext(count + 1) && this.origin.peek(count).matches(pattern)) {
            count++;
        }
        return count;
    }
}
