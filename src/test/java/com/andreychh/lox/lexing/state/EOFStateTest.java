package com.andreychh.lox.lexing.state;

import com.andreychh.lox.Position;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.source.TextSource;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.TokenType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link EOFState}.
 */
final class EOFStateTest {
    @Test
    void createsEOFTokenWithEmptySource() {
        assertEquals(
            new ExplicitToken(TokenType.EOF, "", new Position(1, 1)),
            new EOFState(new TextSource(""), new LexingResult())
                .next()
                .collectResult()
                .tokens()
                .get(0),
            "EOFState does not create EOF token with empty source"
        );
    }

    @Test
    void createsEOFTokenWithNonEmptySource() {
        assertEquals(
            new ExplicitToken(TokenType.EOF, "", new Position(1, 1)),
            new EOFState(new TextSource("content"), new LexingResult())
                .next()
                .collectResult()
                .tokens()
                .get(0),
            "EOFState does not create EOF token with non-empty source"
        );
    }
}
