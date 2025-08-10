package com.andreychh.lox.source;

import com.andreychh.lox.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link TextSource}.
 */
class TextSourceTest {
    @ParameterizedTest
    @CsvSource({"0, a", "1, b", "2, c"})
    void peeksCharacterAtGivenOffset(int offset, String expected) {
        assertEquals(
                expected,
                new TextSource("abc").peek(offset),
                "TextSource failed to peek the character at the given offset"
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    void reportsThatItHasEnoughCharacters(int count) {
        assertTrue(
                new TextSource("abc").hasNext(count),
                "TextSource failed to report that it has enough characters"
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 5})
    void reportsThatItDoesNotHaveEnoughCharacters(int count) {
        assertFalse(
                new TextSource("abc").hasNext(count),
                "TextSource incorrectly reported that it has enough characters"
        );
    }

    @Test
    void reportsItHasNoNextWhenEmpty() {
        assertFalse(
                new TextSource("").hasNext(1),
                "TextSource should have reported that it has no more characters"
        );
    }

    @Test
    void reportsCorrectInitialPosition() {
        assertEquals(
                new Position(1, 1),
                new TextSource("text").position(),
                "TextSource failed to report the initial position as (1, 1)"
        );
    }

    @Test
    void calculatesPositionCorrectlyAfterSeveralLines() {
        assertEquals(
                new Position(3, 1),
                new TextSource("a\nb\nc").skip(4).position(),
                "TextSource failed to calculate the position after advancing over newlines"
        );
    }

    @Test
    void takesCorrectFragmentFromText() {
        assertEquals(
                "abc",
                new TextSource("abcdef").take(3).value(),
                "TextSource failed to take the correct fragment from the text"
        );
    }

    @Test
    void returnsCorrectRemainingSourceAfterTake() {
        assertEquals(
                "c",
                new TextSource("abc").take(2).remaining().peek(0),
                "TextSource failed to return the correct remaining source after take"
        );
    }

    @Test
    void skipsToCorrectOffset() {
        assertEquals(
                "5",
                new TextSource("12345").skip(4).peek(0),
                "TextSource failed to skip to the correct offset"
        );
    }

    @Test
    void handlesNonAsciiCharactersInPeek() {
        assertEquals(
                "б",
                new TextSource("aбв").peek(1),
                "TextSource failed to peek a non-ASCII character"
        );
    }

    @Test
    void handlesNonAsciiCharactersInTake() {
        assertEquals(
                "aб",
                new TextSource("aбв").take(2).value(),
                "TextSource failed to take non-ASCII characters"
        );
    }

    @Test
    void returnsEmptyStringWhenTakeCountIsZero() {
        assertEquals(
                "",
                new TextSource("abc").take(0).value(),
                "TextSource failed to return an empty string when take(0)"
        );
    }

    @Test
    void doesNotAdvanceWhenSkipZero() {
        assertEquals(
                "1",
                new TextSource("123").skip(0).peek(0),
                "TextSource skip(0) should not advance the position"
        );
    }

    @Test
    void returnsWholeTextWhenTakeCountEqualsLength() {
        assertEquals(
                "abc",
                new TextSource("abc").take(3).value(),
                "TextSource failed to return the whole text when take(count==length)"
        );
    }

    @Test
    void returnsEmptyFragmentForEmptyInput() {
        assertEquals(
                "",
                new TextSource("").take(0).value(),
                "TextSource failed to return an empty fragment for empty input"
        );
    }
}
