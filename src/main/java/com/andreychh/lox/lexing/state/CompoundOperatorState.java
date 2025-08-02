package com.andreychh.lox.lexing.state;

import com.andreychh.lox.Source;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenFromLexeme;

public final class CompoundOperatorState implements LexingState {
    private final Source source;
    private final LexingResult result;

    public CompoundOperatorState(final Source source, final LexingResult result) {
        this.source = source;
        this.result = result;
    }

    @Override
    public LexingStep next() {
        LexingState state = switch (this.source.peek(1)) {
            case '=' -> {
                Token token = new TokenFromLexeme(this.source.take(2), this.source.position());
                yield new InitialState(this.source.skip(2), this.result.withToken(token));
            }
            default -> {
                Token token = new TokenFromLexeme(this.source.take(1), this.source.position());
                yield new InitialState(this.source.skip(1), this.result.withToken(token));
            }
        };
        return new LexingStep(state, false);
    }

    @Override
    public LexingResult collectResult() {
        return this.result;
    }
}
