package com.andreychh.lox.lexing.state;

import com.andreychh.lox.Position;
import com.andreychh.lox.Source;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.token.TokenFromLexeme;
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
                new TokenFromLexeme("/", new Position(1, 1)),
                new SlashState(new Source("/ "), new LexingResult())
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
                new TokenFromLexeme("/", new Position(1, 1)),
                new SlashState(new Source("/"), new LexingResult())
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
                new SlashState(new Source("//comment"), new LexingResult())
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
                new SlashState(new Source("//comment\n"), new LexingResult())
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
                new SlashState(new Source("//"), new LexingResult())
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
                new SlashState(new Source("//\n"), new LexingResult())
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
