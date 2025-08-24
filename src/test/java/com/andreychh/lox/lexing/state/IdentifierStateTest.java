package com.andreychh.lox.lexing.state;

import com.andreychh.lox.Position;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.source.TextSource;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.TokenType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link IdentifierState}.
 */
final class IdentifierStateTest {
    @ParameterizedTest
    @MethodSource("keywordCases")
    void recognizesKeyword(String keyword, TokenType type) {
        assertEquals(
            new ExplicitToken(type, keyword, new Position(1, 1)),
            new IdentifierState(new TextSource(keyword), new LexingResult())
                .next()
                .collectResult()
                .tokens()
                .get(0),
            "IdentifierState failed to recognize '%s' as %s token".formatted(keyword, type)
        );
    }

    static Stream<Arguments> keywordCases() {
        return Stream.of(
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
            Arguments.of("while", TokenType.WHILE)
        );
    }

    @Test
    void stopsAtNonAlphanumericCharacter() {
        assertEquals(
            new ExplicitToken(TokenType.VAR, "var", new Position(1, 1)),
            new IdentifierState(new TextSource("var("), new LexingResult())
                .next()
                .collectResult()
                .tokens()
                .get(0),
            "IdentifierState failed to stop parsing at non-alphanumeric character"
        );
    }

    @Test
    void createsIdentifierWithNumbers() {
        assertEquals(
            new ExplicitToken(TokenType.IDENTIFIER, "var123", new Position(1, 1)),
            new IdentifierState(new TextSource("var123"), new LexingResult())
                .next()
                .collectResult()
                .tokens()
                .get(0),
            "IdentifierState failed to create identifier token containing numbers"
        );
    }

    @Test
    void createsIdentifierStartingWithUnderscore() {
        assertEquals(
            new ExplicitToken(TokenType.IDENTIFIER, "_var", new Position(1, 1)),
            new IdentifierState(new TextSource("_var"), new LexingResult())
                .next()
                .collectResult()
                .tokens()
                .get(0),
            "IdentifierState failed to create identifier token starting with underscore"
        );
    }

    @Test
    void createsSingleLetterIdentifier() {
        assertEquals(
            new ExplicitToken(TokenType.IDENTIFIER, "a", new Position(1, 1)),
            new IdentifierState(new TextSource("a"), new LexingResult())
                .next()
                .collectResult()
                .tokens()
                .get(0),
            "IdentifierState failed to create single-letter identifier token"
        );
    }

    @Test
    void treatsUppercaseKeywordAsIdentifier() {
        assertEquals(
            new ExplicitToken(TokenType.IDENTIFIER, "VAR", new Position(1, 1)),
            new IdentifierState(new TextSource("VAR"), new LexingResult())
                .next()
                .collectResult()
                .tokens()
                .get(0),
            "IdentifierState incorrectly recognized uppercase 'VAR' as keyword instead of identifier"
        );
    }

    @Test
    void treatsCapitalizedWordAsIdentifier() {
        assertEquals(
            new ExplicitToken(TokenType.IDENTIFIER, "Variable", new Position(1, 1)),
            new IdentifierState(new TextSource("Variable"), new LexingResult())
                .next()
                .collectResult()
                .tokens()
                .get(0),
            "IdentifierState incorrectly recognized capitalized 'Variable' as keyword instead of identifier"
        );
    }
}
