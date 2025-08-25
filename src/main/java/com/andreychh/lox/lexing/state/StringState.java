package com.andreychh.lox.lexing.state;

import com.andreychh.lox.error.Error;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.source.Fragment;
import com.andreychh.lox.source.PatternSource;
import com.andreychh.lox.source.Source;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenType;

/**
 * Represents a state that processes string literals, handling both valid strings and unterminated string errors.
 *
 * @apiNote Expects {@code source.take(1)} to return {@code '"'}
 */
public final class StringState implements LexingState {
    private final Source source;
    private final LexingResult result;

    /**
     * Creates a new string state.
     *
     * @param source The source code positioned at a string literal
     * @param result The accumulated lexing result
     */
    public StringState(final Source source, final LexingResult result) {
        this.source = source;
        this.result = result;
    }

    /**
     * Consumes characters until closing quote or creates an error for unterminated strings.
     */
    @Override
    public LexingState next() {
        Fragment taken = new PatternSource(this.source.skip(1)).take("[^\"]");
        if (!taken.remaining().hasNext(1)) {
            Error error = new Error("Unterminated string literal", this.source.position());
            return new EOFState(taken.remaining(), this.result.withError(error));
        }
        Token token = new ExplicitToken(TokenType.STRING, "\"%s\"".formatted(taken.value()), this.source.position());
        return new InitialState(taken.remaining().skip(1), this.result.withToken(token));
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
