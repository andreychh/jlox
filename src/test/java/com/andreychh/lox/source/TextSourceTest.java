package com.andreychh.lox.source;

import com.andreychh.lox.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link TextSource}.
 */
class TextSourceTest {

    @Test
    void readsFirstCharacterFromNonEmptySource() {
        assertEquals(
                'a',
                new TextSource("abc").peek(),
                "TextSource was supposed to read the first character but it did not"
        );
    }

    @Test
    void failsToPeekOnEmptySource() {
        assertThrows(
                IllegalStateException.class,
                () -> new TextSource("").peek(),
                "TextSource must not allow peeking from an empty source"
        );
    }

    @Test
    void advancesToNextCharacter() {
        assertEquals(
                'b',
                new TextSource("ab").next().peek(),
                "TextSource failed to advance its position to the next character"
        );
    }

    @Test
    void failsToAdvancePastTheEnd() {
        assertThrows(
                IllegalStateException.class,
                () -> new TextSource("a").next().next(),
                "TextSource must not advance past the end of the source"
        );
    }

    @Test
    void reportsItHasNextWhenNotEmpty() {
        assertTrue(
                new TextSource("abc").hasNext(),
                "TextSource should have reported that it has more characters"
        );
    }

    @Test
    void reportsItHasNoNextWhenEmpty() {
        assertFalse(
                new TextSource("").hasNext(),
                "TextSource should have reported that it has no more characters"
        );
    }

    @Test
    void reportsCorrectInitialPosition() {
        assertEquals(
                new Position(1, 1),
                new TextSource("text").position(),
                "TextSource did not report the correct initial position of (1, 1)"
        );
    }

    @Test
    void calculatesPositionCorrectlyAfterSeveralLines() {
        assertEquals(
                new Position(3, 1),
                new TextSource("a\nb\nc")
                        .next() // \n
                        .next() // b
                        .next() // \n
                        .next() // c
                        .position(),
                "TextSource failed to calculate the correct position after advancing over newlines"
        );
    }

    @Test
    void handlesIrregularUtfCharactersCorrectly() {
        String irregular = "你好, мир! \uD83D\uDE80";
        assertEquals(
                ',',
                new TextSource(irregular).next().next().peek(),
                "TextSource did not handle non-ASCII and supplementary characters correctly"
        );
    }
}
