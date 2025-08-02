package com.andreychh.lox.lexing;

import com.andreychh.lox.*;

/**
 * A terminal state indicating that the lexing process is complete.
 * It is responsible for adding the final EOF token.
 */
public final class FinalState implements LexingState {
    private final Tokens tokens;
    private final Source source;

    public FinalState(final Tokens tokens, final Source source) {
        this.tokens = tokens;
        this.source = source;
    }

    @Override
    public Transition process() {
        throw new UnsupportedOperationException("Cannot process a final state.");
    }

    @Override
    public LexingResult collect() {
        return new LexingResult(this.tokens);
    }
}
