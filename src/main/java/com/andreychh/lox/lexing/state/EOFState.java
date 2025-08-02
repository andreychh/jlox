package com.andreychh.lox.lexing.state;

import com.andreychh.lox.Source;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenType;

/**
 * A state responsible for performing the final action of the lexer:
 * adding the EOF token. After this, it transitions to a permanently
 * terminated state.
 */
public final class EOFState implements LexingState {
    private final Source source;
    private final LexingResult result;

    public EOFState(final Source source, final LexingResult result) {
        this.source = source;
        this.result = result;
    }

    @Override
    public LexingStep next() {
        Token token = new ExplicitToken(TokenType.EOF, "", this.source.position());
        return new LexingStep(new TerminatedState(this.result.withToken(token)), false);
    }

    @Override
    public LexingResult collectResult() {
        return this.result;
    }
}
