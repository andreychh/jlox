package com.andreychh.lox.lexing.state;

import com.andreychh.lox.Source;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenFromLexeme;

/**
 * The initial state of the lexical analysis process.
 * <p>
 * This state is responsible for examining the next character in the source
 * and transitioning to the appropriate specialized state based on the
 * character.
 * It directly handles simple single-character tokens and whitespace.
 */
public final class InitialState implements LexingState {
    private final Source source;
    private final LexingResult result;

    /**
     * Creates a new initial state with the given source and accumulated result.
     *
     * @param source The source code at the current position
     * @param result The accumulated lexing result
     */
    public InitialState(final Source source, final LexingResult result) {
        this.source = source;
        this.result = result;
    }

    /**
     * Creates a new initial state with the given source and an empty result.
     *
     * @param source The source code at the starting position
     */
    public InitialState(final Source source) {
        this(source, new LexingResult());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Examines the next character and transitions to the appropriate state or
     * directly handles simple tokens.
     */
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
            default -> throw new IllegalStateException(
                    "Unexpected character '" + character + "' at position " + this.source.position()
            );
        };
        return new LexingStep(state, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LexingResult collectResult() {
        return this.result;
    }
}
