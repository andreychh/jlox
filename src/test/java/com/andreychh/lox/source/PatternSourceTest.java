package com.andreychh.lox.source;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link PatternSource}.
 */
class PatternSourceTest {
    @Test
    void patternSourceConformsReturnsTrueWhenAllPatternsMatch() {
        assertTrue(
                new PatternSource(new TextSource("abc")).conforms(new String[]{"[a-z]", "[a-z]", "[a-z]"}),
                "PatternSource failed to confirm conformity when all patterns match"
        );
    }

    @Test
    void patternSourceConformsReturnsTrueWhenPrefixMatchesPatterns() {
        assertTrue(
                new PatternSource(new TextSource("abcdef")).conforms(new String[]{"[a-z]", "[a-z]", "[a-z]"}),
                "PatternSource must not confirm conformity when only prefix matches patterns"
        );
    }

    @Test
    void patternSourceConformsReturnsFalseWhenAnyPatternDoesNotMatch() {
        assertFalse(
                new PatternSource(new TextSource("ab1")).conforms(new String[]{"[a-z]", "[a-z]", "[a-z]"}),
                "PatternSource incorrectly confirmed conformity when a pattern does not match"
        );
    }

    @Test
    void patternSourceConformsReturnsFalseWhenNotEnoughCharacters() {
        assertFalse(
                new PatternSource(new TextSource("a")).conforms(new String[]{"[a-z]", "[a-z]"}),
                "PatternSource incorrectly confirmed conformity when not enough characters"
        );
    }

    @Test
    void patternSourceTakeReturnsFragmentWithMatchingPrefix() {
        assertEquals(
                "abc",
                new PatternSource(new TextSource("abc123")).take("[a-z]").value(),
                "PatternSource failed to take the correct matching prefix"
        );
    }

    @Test
    void patternSourceTakeReturnsEmptyFragmentWhenNoMatch() {
        assertEquals(
                "",
                new PatternSource(new TextSource("123")).take("[a-z]").value(),
                "PatternSource failed to return an empty fragment when no match"
        );
    }

    @Test
    void patternSourceSkipSkipsAllMatchingCharacters() {
        assertEquals(
                "BBB",
                new PatternSource(new TextSource("aaaBBB")).skip("[a]").take(3).value(),
                "PatternSource failed to skip all matching characters"
        );
    }

    @Test
    void patternSourceSkipSkipsNothingWhenNoMatch() {
        assertEquals(
                "1",
                new PatternSource(new TextSource("123")).skip("[a-z]").peek(0),
                "PatternSource incorrectly skipped characters when no match"
        );
    }

    @Test
    void patternSourceHandlesNonAsciiCharacters() {
        assertEquals(
                "абв",
                new PatternSource(new TextSource("абв123")).take("[а-я]").value(),
                "PatternSource failed to handle non-ASCII characters"
        );
    }

    @Test
    void patternSourceTakeReturnsWholeTextWhenAllMatch() {
        assertEquals(
                "xyz",
                new PatternSource(new TextSource("xyz")).take("[a-z]").value(),
                "PatternSource failed to take the whole text when all match"
        );
    }
}
