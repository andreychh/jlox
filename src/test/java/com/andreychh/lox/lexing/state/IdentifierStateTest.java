package com.andreychh.lox.lexing.state;

import com.andreychh.lox.Position;
import com.andreychh.lox.Source;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.TokenType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link IdentifierState}.
 */
class IdentifierStateTest {
    @Test
    void recognizesVarKeyword() {
        assertEquals(
                new ExplicitToken(TokenType.VAR, "var", new Position(1, 1)),
                new IdentifierState(new Source("var"), new LexingResult())
                        .next()
                        .state()
                        .collectResult()
                        .tokens()
                        .iterator()
                        .next(),
                "IdentifierState failed to recognize 'var' as a keyword token"
        );
    }

    @Test
    void stopsAtNonAlphanumericCharacter() {
        assertEquals(
                new ExplicitToken(TokenType.VAR, "var", new Position(1, 1)),
                new IdentifierState(new Source("var("), new LexingResult())
                        .next()
                        .state()
                        .collectResult()
                        .tokens()
                        .iterator()
                        .next(),
                "IdentifierState failed to stop parsing at non-alphanumeric character"
        );
    }

    @Test
    void createsIdentifierWithNumbers() {
        assertEquals(
                new ExplicitToken(TokenType.IDENTIFIER, "var123", new Position(1, 1)),
                new IdentifierState(new Source("var123"), new LexingResult())
                        .next()
                        .state()
                        .collectResult()
                        .tokens()
                        .iterator()
                        .next(),
                "IdentifierState failed to create identifier token containing numbers"
        );
    }

    @Test
    void createsIdentifierStartingWithUnderscore() {
        assertEquals(
                new ExplicitToken(TokenType.IDENTIFIER, "_var", new Position(1, 1)),
                new IdentifierState(new Source("_var"), new LexingResult())
                        .next()
                        .state()
                        .collectResult()
                        .tokens()
                        .iterator()
                        .next(),
                "IdentifierState failed to create identifier token starting with underscore"
        );
    }

    @Test
    void createsSingleLetterIdentifier() {
        assertEquals(
                new ExplicitToken(TokenType.IDENTIFIER, "a", new Position(1, 1)),
                new IdentifierState(new Source("a"), new LexingResult())
                        .next()
                        .state()
                        .collectResult()
                        .tokens()
                        .iterator()
                        .next(),
                "IdentifierState failed to create single-letter identifier token"
        );
    }

    @Test
    void treatsUppercaseKeywordAsIdentifier() {
        assertEquals(
                new ExplicitToken(TokenType.IDENTIFIER, "VAR", new Position(1, 1)),
                new IdentifierState(new Source("VAR"), new LexingResult())
                        .next()
                        .state()
                        .collectResult()
                        .tokens()
                        .iterator()
                        .next(),
                "IdentifierState incorrectly recognized uppercase 'VAR' as keyword instead of identifier"
        );
    }

    @Test
    void treatsCapitalizedWordAsIdentifier() {
        assertEquals(
                new ExplicitToken(TokenType.IDENTIFIER, "Variable", new Position(1, 1)),
                new IdentifierState(new Source("Variable"), new LexingResult())
                        .next()
                        .state()
                        .collectResult()
                        .tokens()
                        .iterator()
                        .next(),
                "IdentifierState incorrectly recognized capitalized 'Variable' as keyword instead of identifier"
        );
    }
}
