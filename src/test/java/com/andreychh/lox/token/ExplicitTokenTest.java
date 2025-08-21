package com.andreychh.lox.token;

import com.andreychh.lox.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link ExplicitToken}.
 */
final class ExplicitTokenTest {
    @Test
    void returnsItsLexemeAsProvided() {
        assertEquals(
            "foo",
            new ExplicitToken(TokenType.IDENTIFIER, "foo", new Position(5, 7)).lexeme(),
            "ExplicitToken returns an incorrect lexeme"
        );
    }

    @Test
    void returnsItsLexemeAsProvidedNonASCII() {
        assertEquals(
            "αβγ",
            new ExplicitToken(TokenType.IDENTIFIER, "αβγ", new Position(5, 7)).lexeme(),
            "ExplicitToken returns an incorrect lexeme with non-ASCII characters"
        );
    }

    @Test
    void matchesAnyTypeWhenPresent() {
        assertTrue(
            new ExplicitToken(TokenType.STRING, "\"content\"", new Position(3, 2))
                .hasAnyType(new TokenType[]{TokenType.NUMBER, TokenType.STRING, TokenType.LEFT_BRACE}),
            "ExplicitToken does not match the expected token type"
        );
    }

    @Test
    void doesNotMatchAnyIfTypeIsAbsent() {
        assertFalse(
            new ExplicitToken(TokenType.RIGHT_PAREN, ")", new Position(9, 4))
                .hasAnyType(new TokenType[]{TokenType.NUMBER, TokenType.STRING, TokenType.LEFT_BRACE}),
            "ExplicitToken matches a type that is not present in the expected array"
        );
    }

    @Test
    void doesNotMatchWhenExpectedArrayIsEmpty() {
        assertFalse(
            new ExplicitToken(TokenType.NUMBER, "42", new Position(1, 0))
                .hasAnyType(new TokenType[]{}),
            "ExplicitToken matches when the expected type array is empty"
        );
    }
}
