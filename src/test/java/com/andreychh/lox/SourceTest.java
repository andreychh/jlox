package com.andreychh.lox;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Predicate;

public class SourceTest {
    @Test
    public void testPeek() {
        Source source = new Source("hello world");

        assertEquals('h', source.peek(0));
        assertEquals('e', source.peek(1));
        assertEquals('l', source.peek(2));
        assertEquals('d', source.peek(10));
        assertThrows(IndexOutOfBoundsException.class, () -> source.peek(11));
        assertThrows(IllegalArgumentException.class, () -> source.peek(-1));
    }

    @Test
    public void testPeekEmptySource() {
        Source emptySource = new Source("");

        assertThrows(IndexOutOfBoundsException.class, () -> emptySource.peek(0));
    }

    @Test
    public void testCanPeek() {
        Source source = new Source("hello world");

        assertTrue(source.canPeek(0));
        assertTrue(source.canPeek(10));
        assertFalse(source.canPeek(11));
        assertFalse(source.canPeek(-1));
    }

    @Test
    public void testCanPeekEmptySource() {
        Source emptySource = new Source("");

        assertFalse(emptySource.canPeek(0));
        assertFalse(emptySource.canPeek(-1));
    }

    @Test
    public void testTake() {
        Source source = new Source("hello world");

        assertEquals("h", source.take(1));
        assertEquals("he", source.take(2));
        assertEquals("hello", source.take(5));
        assertEquals("hello world", source.take(11));
        assertThrows(IndexOutOfBoundsException.class, () -> source.take(12));
    }

    @Test
    public void testTakeEmptySource() {
        Source source = new Source("");

        assertEquals("", source.take(0));
        assertThrows(IndexOutOfBoundsException.class, () -> source.take(1));
    }

    @Test
    public void testCanTake() {
        Source source = new Source("hello world");

        assertTrue(source.canTake(0));
        assertTrue(source.canTake(11));
        assertFalse(source.canTake(-1));
        assertFalse(source.canTake(12));
    }

    @Test
    public void testCanTakeEmptySource() {
        Source emptySource = new Source("");

        assertTrue(emptySource.canTake(0));
        assertFalse(emptySource.canTake(-1));
        assertFalse(emptySource.canTake(1));
    }

    @Test
    public void testTakeWhile() {
        Source source = new Source("hello world");

        Predicate<Character> isLetter = Character::isLetter;
        assertEquals("hello", source.takeWhile(isLetter));

        Predicate<Character> isNotSpace = c -> c != ' ';
        assertEquals("hello", source.takeWhile(isNotSpace));

        Predicate<Character> isVowel = c -> "aeiou".indexOf(Character.toLowerCase(c)) >= 0;
        assertEquals("", source.takeWhile(isVowel));

        Source sourceAtE = new Source("hello", 1);
        assertEquals("e", sourceAtE.takeWhile(isVowel));
    }

    @Test
    public void testSkip() {
        Source source = new Source("hello world");

        Source afterOne = source.skip(1);
        assertEquals('e', afterOne.peek(0));

        Source afterFive = source.skip(5);
        assertEquals(' ', afterFive.peek(0));

        Source afterAll = source.skip(11);
        assertFalse(afterAll.canPeek(0));

        assertTrue(source.canSkip(0));
        assertTrue(source.canSkip(11));
        assertFalse(source.canSkip(12));
        assertFalse(source.canSkip(-1));
    }

    @Test
    public void testSkipWhile() {
        Source source = new Source("hello world");

        Predicate<Character> isLetter = Character::isLetter;
        Source afterLetters = source.skipWhile(isLetter);
        assertEquals(' ', afterLetters.peek(0));

        Predicate<Character> isNotSpace = c -> c != ' ';
        Source afterNonSpace = source.skipWhile(isNotSpace);
        assertEquals(' ', afterNonSpace.peek(0));

        Predicate<Character> isSpace = Character::isWhitespace;
        Source afterSpace = afterNonSpace.skipWhile(isSpace);
        assertEquals('w', afterSpace.peek(0));
    }

    @Test
    public void testPosition() {
        Source source = new Source("line1\nline2\nline3");

        assertEquals(new Position(1, 1), source.position());
        assertEquals(new Position(1, 2), source.skip(1).position());

        Source secondLine = source.skip(6);
        assertEquals(new Position(2, 1), secondLine.position());

        Source thirdLine = source.skip(12);
        assertEquals(new Position(3, 1), thirdLine.position());

        assertEquals(new Position(2, 3), source.skip(8).position());
    }
}