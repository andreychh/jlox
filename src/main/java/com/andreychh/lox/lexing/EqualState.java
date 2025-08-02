package com.andreychh.lox.lexing;

import com.andreychh.lox.Source;
import com.andreychh.lox.SimpleToken;
import com.andreychh.lox.TokenType;
import com.andreychh.lox.Tokens;

public final class EqualState implements LexingState {
    private final Tokens tokens;
    private final Source source;

    public EqualState(final Tokens tokens, final Source source) {
        this.tokens = tokens;
        this.source = source;
    }

    @Override
    public LexingState process() {
        char character = this.source.peek(1);
        return switch (character) {
            case '=' -> new InitialState(
                    this.tokens.withToken(new SimpleToken(TokenType.EQUAL_EQUAL, "==", this.source.position())),
                    this.source.skip(2)
            );
            default -> new InitialState(
                    this.tokens.withToken(new SimpleToken(TokenType.EQUAL, "=", this.source.position())),
                    this.source.skip(1)
            );
        };
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
