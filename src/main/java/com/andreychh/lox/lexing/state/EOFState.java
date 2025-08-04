package com.andreychh.lox.lexing.state;

import com.andreychh.lox.Source;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenType;

/**
 * Represents the end-of-file state in the lexical analysis process.
 * <p>
 * This state is reached when the lexer has consumed all input characters.
 * It generates an EOF token and transitions to the terminated state.
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
     * {@inheritDoc}
     * <p>
     * Adds an EOF token to the result and transitions to the terminated state.
     */
    @Override
    public LexingStep next() {
        Token token = new ExplicitToken(TokenType.EOF, "", this.source.position());
        return new LexingStep(new TerminatedState(this.result.withToken(token)), false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LexingResult collectResult() {
        return this.result;
    }
}
