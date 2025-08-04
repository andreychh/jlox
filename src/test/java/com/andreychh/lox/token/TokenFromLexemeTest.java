package com.andreychh.lox.token;

import com.andreychh.lox.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link TokenFromLexeme} class.
 */
class TokenFromLexemeTest {
    /**
     * TokenFromLexeme formats correctly with inferred token type for all valid lexemes.
     */
    @ParameterizedTest
    @MethodSource("lexemeToTokenTypeMapping")
    void formatIncludesInferredTokenType(String lexeme, TokenType expectedType) {
        TokenFromLexeme token = new TokenFromLexeme(lexeme, new Position(1, 1));
        assertEquals(
                String.format("Token(%s, \"%s\", 1:1)", expectedType, lexeme),
                token.format(),
                String.format("Token format for '%s' should include inferred token type %s", lexeme, expectedType)
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

    /**
     * TokenFromLexeme throws exception for unexpected lexeme.
     */
    @Test
    void formatThrowsExceptionForUnknownLexeme() {
        TokenFromLexeme token = new TokenFromLexeme("@unknown$", new Position(1, 1));

        assertThrows(
                IllegalArgumentException.class,
                token::format,
                "Token with unknown lexeme should throw IllegalArgumentException"
        );
    }

    /**
     * Provides test data for token formatting tests.
     *
     * @return Stream of arguments containing lexemes and their expected token types
     */
    private static Stream<Arguments> lexemeToTokenTypeMapping() {
        return Stream.of(
                // Keywords
                Arguments.of("and", TokenType.AND),
                Arguments.of("class", TokenType.CLASS),
                Arguments.of("else", TokenType.ELSE),
                Arguments.of("false", TokenType.FALSE),
                Arguments.of("for", TokenType.FOR),
                Arguments.of("fun", TokenType.FUN),
                Arguments.of("if", TokenType.IF),
                Arguments.of("nil", TokenType.NIL),
                Arguments.of("or", TokenType.OR),
                Arguments.of("print", TokenType.PRINT),
                Arguments.of("return", TokenType.RETURN),
                Arguments.of("super", TokenType.SUPER),
                Arguments.of("this", TokenType.THIS),
                Arguments.of("true", TokenType.TRUE),
                Arguments.of("var", TokenType.VAR),
                Arguments.of("while", TokenType.WHILE),

                // Single-character operators
                Arguments.of("{", TokenType.LEFT_BRACE),
                Arguments.of("}", TokenType.RIGHT_BRACE),
                Arguments.of("(", TokenType.LEFT_PAREN),
                Arguments.of(")", TokenType.RIGHT_PAREN),
                Arguments.of(".", TokenType.DOT),
                Arguments.of(",", TokenType.COMMA),
                Arguments.of(";", TokenType.SEMICOLON),
                Arguments.of("+", TokenType.PLUS),
                Arguments.of("-", TokenType.MINUS),
                Arguments.of("*", TokenType.STAR),
                Arguments.of("/", TokenType.SLASH),
                Arguments.of("!", TokenType.BANG),
                Arguments.of("=", TokenType.EQUAL),
                Arguments.of(">", TokenType.GREATER),
                Arguments.of("<", TokenType.LESS),

                // Two-character operators
                Arguments.of("!=", TokenType.BANG_EQUAL),
                Arguments.of("==", TokenType.EQUAL_EQUAL),
                Arguments.of(">=", TokenType.GREATER_EQUAL),
                Arguments.of("<=", TokenType.LESS_EQUAL)
        );
    }
}
