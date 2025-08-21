package com.andreychh.lox.token.stream;

import java.util.List;

import com.andreychh.lox.Position;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link ListTokenStream}.
 */
final class ListTokenStreamTest {
    @Test
    void returnsTrueWhenCanLookaheadWithinBounds() {
        assertTrue(
            new ListTokenStream(List.of(
                new ExplicitToken(TokenType.NUMBER, "1", new Position(1, 1)),
                new ExplicitToken(TokenType.STRING, "\"α\"", new Position(1, 2))
            )).canLookahead(2),
            "ListTokenStream cannot lookahead within bounds"
        );
    }

    @Test
    void returnsFalseWhenCanLookaheadOutOfBounds() {
        assertFalse(
            new ListTokenStream(List.of(
                new ExplicitToken(TokenType.NUMBER, "1", new Position(1, 1))
            )).canLookahead(2),
            "ListTokenStream can lookahead out of bounds"
        );
    }

    @Test
    void returnsCorrectTokenOnLookahead() {
        Token token = new ExplicitToken(TokenType.NUMBER, "\"β\"", new Position(2, 2));
        assertEquals(
            token,
            new ListTokenStream(List.of(
                new ExplicitToken(TokenType.NUMBER, "1", new Position(1, 1)),
                token
            )).lookahead(1),
            "ListTokenStream returns incorrect token on lookahead"
        );
    }

    @Test
    void consumeReturnsCorrectFragment() {
        List<Token> tokens = List.of(
            new ExplicitToken(TokenType.NUMBER, "1", new Position(1, 1)),
            new ExplicitToken(TokenType.STRING, "\"γ\"", new Position(1, 2))
        );
        assertEquals(
            tokens,
            new ListTokenStream(tokens).consume(2).consumed(),
            "ListTokenStream Fragment contains incorrect consumed tokens"
        );
    }

    @Test
    void advanceReturnsStreamWithCorrectOffset() {
        Token token = new ExplicitToken(TokenType.STRING, "\"δ\"", new Position(1, 2));
        assertEquals(
            token,
            new ListTokenStream(List.of(
                new ExplicitToken(TokenType.NUMBER, "1", new Position(1, 1)),
                token
            )).advance(1).lookahead(0),
            "ListTokenStream advance does not return stream with correct offset"
        );
    }

    @Test
    void lookaheadMatchesReturnsTrueForMatchingTypes() {
        assertTrue(
            new ListTokenStream(List.of(
                new ExplicitToken(TokenType.NUMBER, "1", new Position(1, 1)),
                new ExplicitToken(TokenType.STRING, "\"ε\"", new Position(1, 2))
            )).lookaheadMatches(new TokenType[][]{{TokenType.NUMBER}, {TokenType.STRING}}),
            "ListTokenStream lookaheadMatches returns false for matching types"
        );
    }

    @Test
    void lookaheadMatchesReturnsFalseForNonMatchingTypes() {
        assertFalse(
            new ListTokenStream(List.of(
                new ExplicitToken(TokenType.NUMBER, "1", new Position(1, 1)),
                new ExplicitToken(TokenType.STRING, "\"ζ\"", new Position(1, 2))
            )).lookaheadMatches(new TokenType[][]{{TokenType.STRING}, {TokenType.NUMBER}}),
            "ListTokenStream lookaheadMatches returns true for non-matching types"
        );
    }

    @Test
    void lookaheadMatchesReturnsFalseWhenNotEnoughTokens() {
        assertFalse(
            new ListTokenStream(List.of(
                new ExplicitToken(TokenType.NUMBER, "1", new Position(1, 1))
            )).lookaheadMatches(new TokenType[][]{{TokenType.NUMBER}, {TokenType.STRING}}),
            "ListTokenStream lookaheadMatches returns true when not enough tokens"
        );
    }
}
