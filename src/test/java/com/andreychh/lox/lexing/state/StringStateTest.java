package com.andreychh.lox.lexing.state;

import com.andreychh.lox.Position;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.source.TextSource;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.TokenType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Tests for {@link StringState}.
 */
final class StringStateTest {
    @Test
    void createsStringTokenFromSimpleLiteral() {
        assertEquals(
            new ExplicitToken(TokenType.STRING, "\"abc\"", new Position(1, 1)),
            new StringState(new TextSource("\"abc\""), new LexingResult())
                .next()
                .collectResult()
                .tokens()
                .get(0),
            "StringState does not create string token from simple literal"
        );
    }

    @Test
    void createsEmptyStringToken() {
        assertEquals(
            new ExplicitToken(TokenType.STRING, "\"\"", new Position(1, 1)),
            new StringState(new TextSource("\"\""), new LexingResult())
                .next()
                .collectResult()
                .tokens()
                .get(0),
            "StringState does not create empty string token"
        );
    }

    @Test
    void createsStringTokenWithNewlineCharacter() {
        assertEquals(
            new ExplicitToken(TokenType.STRING, "\"abc\n\"", new Position(1, 1)),
            new StringState(new TextSource("\"abc\n\""), new LexingResult())
                .next()
                .collectResult()
                .tokens()
                .get(0),
            "StringState does not create string token containing newline character"
        );
    }

    @Test
    void generatesErrorForUnterminatedString() {
        assertFalse(
            new StringState(new TextSource("\"abc"), new LexingResult())
                .next()
                .collectResult()
                .errors()
                .isEmpty(),
            "StringState does not generate error for unterminated string literal"
        );
    }

    @Test
    void handlesUnicodeCharactersInString() {
        assertEquals(
            new ExplicitToken(TokenType.STRING, "\"你好, мир! \uD83D\uDE80\"", new Position(1, 1)),
            new StringState(new TextSource("\"你好, мир! \uD83D\uDE80\""), new LexingResult())
                .next()
                .collectResult()
                .tokens()
                .get(0),
            "StringState does not handle Unicode characters in string literal"
        );
    }
}
