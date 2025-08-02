package com.andreychh.lox.lexing;


import com.andreychh.lox.Source;
import com.andreychh.lox.Tokens;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenFromLexeme;

public final class InitialState implements LexingState {
    private final Tokens tokens;
    private final Source source;

    public InitialState(final Tokens tokens, final Source source) {
        this.tokens = tokens;
        this.source = source;
    }

    @Override
    public Transition process() {
        if (!this.source.canPeek(0)) {
            return new Transition(new FinalState(this.tokens, this.source), false);
        }
        char character = this.source.peek(0);
        LexingState state = switch (character) {
            case ' ', '\t', '\n', '\r' -> new InitialState(this.tokens, this.source.skip(1));
            case '(', ')', '{', '}', '.', ',', ';', '+', '-', '*' -> {
                Token token = new TokenFromLexeme("" + character, this.source.position());
                yield new InitialState(this.tokens.withToken(token), this.source.skip(1));
            }
            case '!', '=', '>', '<' -> new OperatorState(this.tokens, this.source);
            case '/' -> new SlashState(this.tokens, this.source);
            case '"' -> new StringState(this.tokens, this.source);
            default -> throw new IllegalStateException(
                    "Unexpected character '" + character + "' at position " + this.source.position()
            );
        };
        return new Transition(state, false);
    }

    @Override
    public LexingResult collect() {
        return new LexingResult(this.tokens);
    }
}
