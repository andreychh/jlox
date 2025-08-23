package com.andreychh.lox.lexing.state;

import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.source.PatternSource;
import com.andreychh.lox.source.Source;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.TokenType;

/**
 * Represents a state that handles slash characters, distinguishing between division operators
 * and line comments.
 *
 * @apiNote Expects {@code source.take(1)} to return {@code '/'}
 */
public final class SlashState implements LexingState {
    private final Source source;
    private final LexingResult result;

    /**
     * Creates a new slash state.
     *
     * @param source The source code positioned at a slash character
     * @param result The accumulated lexing result
     */
    public SlashState(final Source source, final LexingResult result) {
        this.source = source;
        this.result = result;
    }

    /**
     * Creates a SLASH token or skips a line comment based on the following character.
     */
    @Override
    public LexingState next() {
        return this.isComment() ? new InitialState(
            new PatternSource(this.source.skip(2)).take("[^\\n]").remaining(),
            this.result
        ) : new InitialState(
            this.source.skip(1),
            this.result.withToken(new ExplicitToken(TokenType.SLASH, "/", this.source.position()))
        );
    }

    /**
     * Checks if this is the start of a line comment ("//").
     *
     * @return {@code true} if the next character is also a slash, {@code false} otherwise
     */
    private boolean isComment() {
        return this.source.hasNext(2) && this.source.peek(1).equals("/");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFinal() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LexingResult collectResult() {
        return this.result;
    }
}
