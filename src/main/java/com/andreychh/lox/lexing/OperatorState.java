package com.andreychh.lox.lexing;

import com.andreychh.lox.Source;
import com.andreychh.lox.Tokens;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenFromLexeme;

public final class OperatorState implements LexingState {

    private final Tokens tokens;
    private final Source source;

    public OperatorState(final Tokens tokens, final Source source) {
        this.tokens = tokens;
        this.source = source;
    }

    @Override
    public Transition process() {
        LexingState state = switch (this.source.peek(1)) {
            case '=' -> {
                Token token = new TokenFromLexeme(this.source.take(2), this.source.position());
                yield new InitialState(this.tokens.withToken(token), this.source.skip(2));
            }
            default -> {
                Token token = new TokenFromLexeme(this.source.take(1), this.source.position());
                yield new InitialState(this.tokens.withToken(token), this.source.skip(1));
            }
        };
        return new Transition(state, false);
    }

    @Override
    public LexingResult collect() {
        return new LexingResult(this.tokens);
    }
}
