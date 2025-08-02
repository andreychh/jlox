package com.andreychh.lox.lexing;

import java.util.ArrayList;
import java.util.List;

import com.andreychh.lox.Error;
import com.andreychh.lox.token.Token;


public final class LexingResult {
    private List<Token> tokens;
    private List<Error> errors;

    public LexingResult(final List<Token> tokens, final List<Error> errors) {
        this.tokens = tokens;
        this.errors = errors;
    }

    public LexingResult() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    public LexingResult withToken(final Token token) {
        List<Token> tokens = new ArrayList<>(this.tokens);
        tokens.add(token);
        return new LexingResult(tokens, this.errors);
    }

    public LexingResult withError(final Error error) {
        List<Error> errors = new ArrayList<>(this.errors);
        errors.add(error);
        return new LexingResult(this.tokens, errors);
    }

    public Iterable<Token> tokens() {
        return this.tokens;
    }

    public Iterable<Error> errors() {
        return this.errors;
    }
}
