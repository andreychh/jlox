package com.andreychh.lox.lexing;

import com.andreychh.lox.Error;
import com.andreychh.lox.Position;
import com.andreychh.lox.source.TextSource;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenFromLexeme;
import com.andreychh.lox.token.TokenType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link LexingFSM} class.
 */
class LexingFSMTest {
    /**
     * FSM tokenizes empty source correctly.
     */
    @Test
    void emptySourceYieldsOnlyEOFToken() {
        LexingFSM fsm = new LexingFSM(new TextSource(""));
        LexingResult result = fsm.tokenize();
        List<Token> tokens = new ArrayList<>();
        result.tokens().forEach(tokens::add);
        assertEquals(
                1,
                tokens.size(),
                "Empty source must produce exactly one EOF token"
        );
    }

    /**
     * FSM produces EOF token at the end of source.
     */
    @Test
    void tokenizeProducesEOFTokenAtTheEnd() {
        LexingFSM fsm = new LexingFSM(new TextSource("("));
        LexingResult result = fsm.tokenize();
        List<Token> tokens = new ArrayList<>();
        result.tokens().forEach(tokens::add);
        assertEquals(
                new ExplicitToken(TokenType.EOF, "", new Position(1, 2)),
                tokens.getLast(),
                "Last token must be EOF at position after content"
        );
    }

    /**
     * FSM correctly tokenizes simple single-character operators.
     */
    @ParameterizedTest
    @ValueSource(strings = {"(", ")", "+", "-", "{", "}", "*", ".", ",", ";"})
    void tokenizesSingleCharacterOperator(final String operator) {
        final LexingFSM fsm = new LexingFSM(new TextSource(operator));
        final LexingResult result = fsm.tokenize();
        final List<Token> tokens = new ArrayList<>();
        result.tokens().forEach(tokens::add);
        assertEquals(
                new TokenFromLexeme(operator, new Position(1, 1)),
                tokens.getFirst(),
                "First token must be '%s' operator".formatted(operator)
        );
    }

    /**
     * FSM tokenizes equality operator.
     */
    @Test
    void tokenizesEqualityOperator() {
        LexingFSM fsm = new LexingFSM(new TextSource("=="));
        LexingResult result = fsm.tokenize();
        List<Token> tokens = new ArrayList<>();
        result.tokens().forEach(tokens::add);
        assertEquals(
                new TokenFromLexeme("==", new Position(1, 1)),
                tokens.getFirst(),
                "First token must be equality operator"
        );
    }

    /**
     * FSM tokenizes multiple simple tokens.
     */
    @Test
    void tokenizesMultipleSimpleTokens() {
        LexingFSM fsm = new LexingFSM(new TextSource("()"));
        LexingResult result = fsm.tokenize();
        List<Token> tokens = new ArrayList<>();
        result.tokens().forEach(tokens::add);
        assertEquals(
                3,
                tokens.size(),
                "Source with '()' must produce 3 tokens (left paren, right paren, EOF)"
        );
    }

    /**
     * FSM reports error for unexpected character.
     */
    @Test
    void reportsErrorForUnexpectedCharacter() {
        LexingFSM fsm = new LexingFSM(new TextSource("^"));
        LexingResult result = fsm.tokenize();
        List<Error> errors = new ArrayList<>();
        result.errors().forEach(errors::add);
        assertEquals(
                1,
                errors.size(),
                "Source with unexpected character must produce error"
        );
    }

    /**
     * FSM ignores whitespace.
     */
    @Test
    void ignoresWhitespace() {
        LexingFSM fsm = new LexingFSM(new TextSource(" \t\n\r"));
        LexingResult result = fsm.tokenize();
        List<Token> tokens = new ArrayList<>();
        result.tokens().forEach(tokens::add);
        assertEquals(
                1,
                tokens.size(),
                "Source with only whitespace must produce just EOF token"
        );
    }

    /**
     * FSM preserves positions across whitespace.
     */
    @Test
    void preservesPositionsAcrossWhitespace() {
        LexingFSM fsm = new LexingFSM(new TextSource(" \n +"));
        LexingResult result = fsm.tokenize();
        List<Token> tokens = new ArrayList<>();
        result.tokens().forEach(tokens::add);
        assertEquals(
                new TokenFromLexeme("+", new Position(2, 2)),
                tokens.getFirst(),
                "Token after whitespace must have correct position"
        );
    }
}
