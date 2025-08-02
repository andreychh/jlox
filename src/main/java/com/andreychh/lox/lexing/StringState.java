package com.andreychh.lox.lexing;

import com.andreychh.lox.Source;
import com.andreychh.lox.SimpleToken;
import com.andreychh.lox.TokenType;
import com.andreychh.lox.Tokens;

public final class StringState implements LexingState {
    private final Tokens tokens;
    private final Source source;

    public StringState(final Tokens tokens, final Source source) {
        this.tokens = tokens;
        this.source = source;
    }

    @Override
    public LexingState process() {
        Source source = this.source.skip(1).skipWhile(c -> c != '"').skip(1);
        return new InitialState(
                this.tokens.withToken(
                        new SimpleToken(TokenType.STRING, source.sliceUntil(this.source), this.source.position())
                ),
                source
        );
    }

    @Override
    public boolean isTerminal() {
        return false;
    }

    @Override
    public Tokens tokens() {
        return this.tokens;
    }
}
