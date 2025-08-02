package com.andreychh.lox.lexing.state;


import com.andreychh.lox.Source;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenFromLexeme;

public final class InitialState implements LexingState {
    private final Source source;
    private final LexingResult result;

    public InitialState(final Source source, final LexingResult result) {
        this.source = source;
        this.result = result;
    }

    public InitialState(final Source source) {
        this(source, new LexingResult());
    }

    @Override
    public LexingStep next() {
        if (!this.source.canPeek(0)) {
            return new LexingStep(new EOFState(this.source, this.result), false);
        }
        char character = this.source.peek(0);
        LexingState state = switch (character) {
            case ' ', '\t', '\n', '\r' -> new InitialState(this.source.skip(1), this.result);
            case '(', ')', '{', '}', '.', ',', ';', '+', '-', '*' -> {
                Token token = new TokenFromLexeme("" + character, this.source.position());
                yield new InitialState(this.source.skip(1), this.result.withToken(token));
            }
            case '!', '=', '>', '<' -> new CompoundOperatorState(this.source, this.result);
            case '/' -> new SlashState(this.source, this.result);
            case '"' -> new StringState(this.source, this.result);
            default -> throw new IllegalStateException(
                    "Unexpected character '" + character + "' at position " + this.source.position()
            );
        };
        return new LexingStep(state, false);
    }

    @Override
    public LexingResult collectResult() {
        return this.result;
    }
}
