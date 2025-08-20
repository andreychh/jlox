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
 * Tests for {@link SlashState}.
 */
class SlashStateTest {
    @Test
    void createsSlashTokenWhenFollowedBySpace() {
        assertEquals(
            new ExplicitToken(TokenType.SLASH, "/", new Position(1, 1)),
            new SlashState(new TextSource("/ "), new LexingResult())
                .next()
                .state()
                .collectResult()
                .tokens()
                .iterator()
                .next(),
            "SlashState failed to create slash token when followed by space"
        );
    }

    @Test
    void createsSlashTokenWhenAtEndOfSource() {
        assertEquals(
            new ExplicitToken(TokenType.SLASH, "/", new Position(1, 1)),
            new SlashState(new TextSource("/"), new LexingResult())
                .next()
                .state()
                .collectResult()
                .tokens()
                .iterator()
                .next(),
            "SlashState failed to create slash token when at end of source"
        );
    }

    @Test
    void skipsCommentWithoutCreatingToken() {
        assertFalse(
            new SlashState(new TextSource("//comment"), new LexingResult())
                .next()
                .state()
                .collectResult()
                .tokens()
                .iterator()
                .hasNext(),
            "SlashState should not create token when processing line comment"
        );
    }

    @Test
    void skipsCommentUntilNewline() {
        assertFalse(
            new SlashState(new TextSource("//comment\n"), new LexingResult())
                .next()
                .state()
                .collectResult()
                .tokens()
                .iterator()
                .hasNext(),
            "SlashState should not create token when processing line comment ending with newline"
        );
    }

    @Test
    void handlesDoubleSlashAtEndOfSource() {
        assertFalse(
            new SlashState(new TextSource("//"), new LexingResult())
                .next()
                .state()
                .collectResult()
                .tokens()
                .iterator()
                .hasNext(),
            "SlashState should not create token for double slash at end of source"
        );
    }

    @Test
    void handlesDoubleSlashFollowedByNewline() {
        assertFalse(
            new SlashState(new TextSource("//\n"), new LexingResult())
                .next()
                .state()
                .collectResult()
                .tokens()
                .iterator()
                .hasNext(),
            "SlashState should not create token for double slash followed immediately by newline"
        );
    }
}
