package com.andreychh.lox.token;

import com.andreychh.lox.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link TokenFromLexeme} class.
 */
class TokenFromLexemeTest {

    /**
     * TokenFromLexeme formats correctly with inferred token type.
     */
    @Test
    void formatIncludesInferredTokenType() {
        TokenFromLexeme token = new TokenFromLexeme("(", new Position(1, 1));
        assertEquals(
                "Token(LEFT_PAREN, \"(\", 1:1)",
                token.format(),
                "Token format should include inferred token type"
        );
    }

    /**
     * TokenFromLexeme equals method returns true for same values.
     */
    @Test
    void equalsReturnsTrueForSameValues() {
        TokenFromLexeme token1 = new TokenFromLexeme("(", new Position(1, 1));
        TokenFromLexeme token2 = new TokenFromLexeme("(", new Position(1, 1));
        assertEquals(
                token1,
                token2,
                "Tokens with same lexeme and position should be equal"
        );
    }

    /**
     * TokenFromLexeme equals method returns false for different lexemes.
     */
    @Test
    void equalsReturnsFalseForDifferentLexemes() {
        TokenFromLexeme token1 = new TokenFromLexeme("(", new Position(1, 1));
        TokenFromLexeme token2 = new TokenFromLexeme(")", new Position(1, 1));
        assertNotEquals(
                token1,
                token2,
                "Tokens with different lexemes should not be equal"
        );
    }

    /**
     * TokenFromLexeme equals method returns false for different positions.
     */
    @Test
    void equalsReturnsFalseForDifferentPositions() {
        TokenFromLexeme token1 = new TokenFromLexeme("(", new Position(1, 1));
        TokenFromLexeme token2 = new TokenFromLexeme("(", new Position(2, 1));
        assertNotEquals(
                token1,
                token2,
                "Tokens with different positions should not be equal"
        );
    }
}
