package com.andreychh.lox.lexing;


import com.andreychh.lox.Source;
import com.andreychh.lox.Tokens;
import com.andreychh.lox.token.SimpleToken;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenType;

public final class StringState implements LexingState {
    private final Tokens tokens;
    private final Source source;

    public StringState(final Tokens tokens, final Source source) {
        this.tokens = tokens;
        this.source = source;
    }

    @Override
    public Transition process() {
        Source source = this.source.skip(1).skipWhile(c -> c != '"').skip(1);
        Token token = new SimpleToken(TokenType.STRING, source.sliceUntil(this.source), this.source.position());
        LexingState state = new InitialState(this.tokens.withToken(token), source);
        return new Transition(state, false);
    }

    @Override
    public LexingResult collect() {
        return new LexingResult(this.tokens);
    }
}
