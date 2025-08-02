package com.andreychh.lox.lexing.state;


import com.andreychh.lox.Source;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenType;

public final class StringState implements LexingState {
    private final Source source;
    private final LexingResult result;

    public StringState(final Source source, final LexingResult result) {
        this.source = source;
        this.result = result;
    }

    @Override
    public LexingStep next() {
        Source source = this.source.skip(1).skipWhile(c -> c != '"').skip(1);
        Token token = new ExplicitToken(TokenType.STRING, source.sliceUntil(this.source), this.source.position());
        LexingState state = new InitialState(source, this.result.withToken(token));
        return new LexingStep(state, false);
    }

    @Override
    public LexingResult collectResult() {
        return this.result;
    }
}
