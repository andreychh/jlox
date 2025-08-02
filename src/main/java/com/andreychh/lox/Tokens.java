package com.andreychh.lox;

import java.util.ArrayList;
import java.util.List;

import com.andreychh.lox.token.Token;

public final class Tokens {
    private final List<Token> tokens;

    public Tokens(final List<Token> tokens) {
        this.tokens = tokens;
    }

    public Tokens() {
        this(new ArrayList<>());
    }

    public Tokens withToken(final Token token) {
        List<Token> tokens = new ArrayList<>(this.tokens);
        tokens.add(token);
        return new Tokens(tokens);
    }
}
