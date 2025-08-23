package com.andreychh.lox.lexing.state;

import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.source.Source;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenType;

/**
 * Represents the end-of-file state that generates an EOF token and transitions to terminated state.
 */
public final class EOFState implements LexingState {
    private final Source source;
    private final LexingResult result;

    /**
     * Creates a new EOF state.
     *
     * @param source The source code at the final position
     * @param result The accumulated lexing result
     */
    public EOFState(final Source source, final LexingResult result) {
        this.source = source;
        this.result = result;
    }

    /**
     * Adds an EOF token and transitions to terminated state.
     */
    @Override
    public LexingState next() {
        Token token = new ExplicitToken(TokenType.EOF, "", this.source.position());
        return new TerminatedState(this.result.withToken(token));
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
