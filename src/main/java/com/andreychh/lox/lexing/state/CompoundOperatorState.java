package com.andreychh.lox.lexing.state;

import com.andreychh.lox.Source;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenFromLexeme;

/**
 * Handles operators that can be either single or double character tokens.
 * <p>
 * This state processes ambiguous operators like '!', '=', '>', '<' which can
 * be followed by an '=' to form compound operators ('!=', '==', '>=', '<=').
 * It looks ahead by one character to resolve this ambiguity and produces the
 * appropriate token.
 */
public final class CompoundOperatorState implements LexingState {
    private final Source source;
    private final LexingResult result;

    /**
     * Creates a new compound operator state.
     *
     * @param source The source code at the current position
     * @param result The accumulated lexing result
     */
    public CompoundOperatorState(final Source source, final LexingResult result) {
        this.source = source;
        this.result = result;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Examines the next character to determine if it forms a compound operator
     * with the current character, and creates the appropriate token.
     */
    @Override
    public LexingStep next() {
        if (this.source.canPeek(1) && this.source.peek(1) == '=') {
            Token token = new TokenFromLexeme(this.source.take(2), this.source.position());
            LexingState nextState = new InitialState(this.source.skip(2), this.result.withToken(token));
            return new LexingStep(nextState, false);
        } else {
            Token token = new TokenFromLexeme(this.source.take(1), this.source.position());
            LexingState nextState = new InitialState(this.source.skip(1), this.result.withToken(token));
            return new LexingStep(nextState, false);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LexingResult collectResult() {
        return this.result;
    }
}
