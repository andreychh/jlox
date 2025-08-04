package com.andreychh.lox.token;

import com.andreychh.lox.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link ExplicitToken} class.
 */
class ExplicitTokenTest {

    /**
     * ExplicitToken formats correctly with all its components.
     */
    @Test
    void formatReturnsFormattedStringRepresentation() {
        ExplicitToken token = new ExplicitToken(TokenType.EOF, "", new Position(1, 1));
        assertEquals(
                "Token(EOF, \"\", 1:1)",
                token.format(),
                "Token format should follow 'Token(type, \"lexeme\", position)' pattern"
        );
    }

    /**
     * ExplicitToken equals method considers all fields.
     */
    @Test
    void equalsReturnsTrueForSameValues() {
        ExplicitToken token1 = new ExplicitToken(TokenType.LEFT_PAREN, "(", new Position(1, 1));
        ExplicitToken token2 = new ExplicitToken(TokenType.LEFT_PAREN, "(", new Position(1, 1));
        assertEquals(
                token1,
                token2,
                "Tokens with same values should be equal"
        );
    }

    /**
     * ExplicitToken equals method returns false for different types.
     */
    @Test
    void equalsReturnsFalseForDifferentTypes() {
        ExplicitToken token1 = new ExplicitToken(TokenType.LEFT_PAREN, "(", new Position(1, 1));
        ExplicitToken token2 = new ExplicitToken(TokenType.RIGHT_PAREN, "(", new Position(1, 1));
        assertNotEquals(
                token1,
                token2,
                "Tokens with different types should not be equal"
        );
    }

    /**
     * ExplicitToken equals method returns false for different lexemes.
     */
    @Test
    void equalsReturnsFalseForDifferentLexemes() {
        ExplicitToken token1 = new ExplicitToken(TokenType.LEFT_PAREN, "(", new Position(1, 1));
        ExplicitToken token2 = new ExplicitToken(TokenType.LEFT_PAREN, "x", new Position(1, 1));
        assertNotEquals(
                token1,
                token2,
                "Tokens with different lexemes should not be equal"
        );
    }

    /**
     * ExplicitToken equals method returns false for different positions.
     */
    @Test
    void equalsReturnsFalseForDifferentPositions() {
        ExplicitToken token1 = new ExplicitToken(TokenType.LEFT_PAREN, "(", new Position(1, 1));
        ExplicitToken token2 = new ExplicitToken(TokenType.LEFT_PAREN, "(", new Position(2, 1));
        assertNotEquals(
                token1,
                token2,
                "Tokens with different positions should not be equal"
        );
    }
}
