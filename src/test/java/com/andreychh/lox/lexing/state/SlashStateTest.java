package com.andreychh.lox.lexing.state;

import com.andreychh.lox.Position;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.source.TextSource;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.TokenType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link SlashState}.
 */
final class SlashStateTest {
    @Test
    void createsSlashTokenWhenFollowedBySpace() {
        assertEquals(
            new ExplicitToken(TokenType.SLASH, "/", new Position(1, 1)),
            new SlashState(new TextSource("/ "), new LexingResult())
                .next()
                .collectResult()
                .tokens()
                .get(0),
            "SlashState failed to create slash token when followed by space"
        );
    }

    @Test
    void createsSlashTokenWhenAtEndOfSource() {
        assertEquals(
            new ExplicitToken(TokenType.SLASH, "/", new Position(1, 1)),
            new SlashState(new TextSource("/"), new LexingResult())
                .next()
                .collectResult()
                .tokens()
                .get(0),
            "SlashState failed to create slash token when at end of source"
        );
    }

    @Test
    void skipsCommentWithoutCreatingToken() {
        assertTrue(
            new SlashState(new TextSource("//comment"), new LexingResult())
                .next()
                .collectResult()
                .tokens()
                .isEmpty(),
            "SlashState should not create token when processing line comment"
        );
    }

    @Test
    void skipsCommentUntilNewline() {
        assertTrue(
            new SlashState(new TextSource("//comment\n"), new LexingResult())
                .next()
                .collectResult()
                .tokens()
                .isEmpty(),
            "SlashState should not create token when processing line comment ending with newline"
        );
    }

    @Test
    void handlesDoubleSlashAtEndOfSource() {
        assertTrue(
            new SlashState(new TextSource("//"), new LexingResult())
                .next()
                .collectResult()
                .tokens()
                .isEmpty(),
            "SlashState should not create token for double slash at end of source"
        );
    }

    @Test
    void handlesDoubleSlashFollowedByNewline() {
        assertTrue(
            new SlashState(new TextSource("//\n"), new LexingResult())
                .next()
                .collectResult()
                .tokens()
                .isEmpty(),
            "SlashState should not create token for double slash followed immediately by newline"
        );
    }
}
