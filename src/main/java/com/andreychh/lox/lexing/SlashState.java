package com.andreychh.lox.lexing;


import com.andreychh.lox.Source;
import com.andreychh.lox.token.TokenFromLexeme;

public final class SlashState implements LexingState {
    private final Source source;
    private final LexingResult result;

    public SlashState(final Source source, final LexingResult result) {
        this.source = source;
        this.result = result;
    }

    @Override
    public LexingStep next() {
        LexingState state = switch (this.source.peek(1)) {
            case '/' -> new InitialState(
                    this.source.skip(2).skipWhile(c -> c != '\n'),
                    this.result
            );
            default -> new InitialState(
                    this.source.skip(1),
                    this.result.withToken(new TokenFromLexeme("/", this.source.position()))
            );
        };
        return new LexingStep(state, false);
    }

    @Override
    public LexingResult collectResult() {
        return this.result;
    }
}
