package com.andreychh.lox;

import java.util.ArrayList;
import java.util.List;


public final class Tokens {
    private final List<SimpleToken> tokens;

    public Tokens(final List<SimpleToken> tokens) {
        this.tokens = tokens;
    }

    public Tokens() {
        this(new ArrayList<>());
    }

    public Tokens withToken(final SimpleToken token) {
        List<SimpleToken> tokens = new ArrayList<>(this.tokens);
        tokens.add(token);
        return new Tokens(tokens);
    }
}
