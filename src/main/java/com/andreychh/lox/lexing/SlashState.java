package com.andreychh.lox.lexing;


import com.andreychh.lox.Source;
import com.andreychh.lox.Tokens;
import com.andreychh.lox.token.TokenFromLexeme;

public final class SlashState implements LexingState {
    private final Tokens tokens;
    private final Source source;

    public SlashState(final Tokens tokens, final Source source) {
        this.tokens = tokens;
        this.source = source;
    }

    @Override
    public Transition process() {
        LexingState state = switch (this.source.peek(1)) {
            case '/' -> new InitialState(
                    this.tokens,
                    this.source.skip(2).skipWhile(c -> c != '\n')
            );
            default -> new InitialState(
                    this.tokens.withToken(new TokenFromLexeme("/", this.source.position())),
                    this.source.skip(1)
            );
        };
        return new Transition(state, false);
    }

    @Override
    public LexingResult collect() {
        return null;
    }
}
