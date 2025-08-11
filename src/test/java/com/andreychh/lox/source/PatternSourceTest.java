package com.andreychh.lox.source;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link PatternSource}.
 */
class PatternSourceTest {
    @Test
    void returnsTrueWhenAllPatternsMatch() {
        assertTrue(
                new PatternSource(new TextSource("abc")).matches(new String[]{"[a-z]", "[a-z]", "[a-z]"}),
                "PatternSource failed to confirm conformity when all patterns match"
        );
    }

    @Test
    void returnsTrueWhenPrefixMatchesPatterns() {
        assertTrue(
                new PatternSource(new TextSource("abcdef")).matches(new String[]{"[a-z]", "[a-z]", "[a-z]"}),
                "PatternSource must not confirm conformity when only prefix matches patterns"
        );
    }

    @Test
    void returnsFalseWhenAnyPatternDoesNotMatch() {
        assertFalse(
                new PatternSource(new TextSource("ab1")).matches(new String[]{"[a-z]", "[a-z]", "[a-z]"}),
                "PatternSource incorrectly confirmed conformity when a pattern does not match"
        );
    }

    @Test
    void returnsFalseWhenNotEnoughCharactersToMatchPatterns() {
        assertFalse(
                new PatternSource(new TextSource("a")).matches(new String[]{"[a-z]", "[a-z]"}),
                "PatternSource incorrectly confirmed conformity when not enough characters"
        );
    }

    @Test
    void returnsFragmentWithMatchingPrefixWhenTakeIsCalled() {
        assertEquals(
                "abc",
                new PatternSource(new TextSource("abc123")).take("[a-z]").value(),
                "PatternSource failed to take the correct matching prefix"
        );
    }

    @Test
    void returnsEmptyFragmentWhenNoCharactersMatchPattern() {
        assertEquals(
                "",
                new PatternSource(new TextSource("123")).take("[a-z]").value(),
                "PatternSource failed to return an empty fragment when no match"
        );
    }

    @Test
    void skipsAllMatchingCharactersWhenSkipIsCalled() {
        assertEquals(
                "BBB",
                new PatternSource(new TextSource("aaaBBB")).skip("a").take(3).value(),
                "PatternSource failed to skip all matching characters"
        );
    }

    @Test
    void skipsNothingWhenNoCharactersMatchPattern() {
        assertEquals(
                "1",
                new PatternSource(new TextSource("123")).skip("[a-z]").peek(0),
                "PatternSource incorrectly skipped characters when no match"
        );
    }

    @Test
    void handlesNonAsciiCharactersWhenTakingByPattern() {
        assertEquals(
                "абв",
                new PatternSource(new TextSource("абв123")).take("[а-я]").value(),
                "PatternSource failed to handle non-ASCII characters"
        );
    }

    @Test
    void returnsWholeTextWhenAllCharactersMatchPattern() {
        assertEquals(
                "xyz",
                new PatternSource(new TextSource("xyz")).take("[a-z]").value(),
                "PatternSource failed to take the whole text when all match"
        );
    }
}
