package com.andreychh.lox.lexing.state;

import com.andreychh.lox.Source;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.TokenType;

/**
 * A lexing state for processing numeric literals.
 * <p>
 * This state is entered when a digit character is encountered in the source code.
 * <p>
 * {@snippet :
 * // Creates a number token for "123"
 * NumberState state = new NumberState(new Source("123"), new LexingResult());
 * LexingStep step = state.next(); // Returns number token
 *
 * // Creates a number token for "123.45"
 * NumberState decimalState = new NumberState(new Source("123.45"), new LexingResult());
 * LexingStep decimalStep = decimalState.next(); // Returns number token
 *
 * // Creates a number token for "42.0"
 * NumberState zeroDecimalState = new NumberState(new Source("42.0"), new LexingResult());
 * LexingStep zeroStep = zeroDecimalState.next(); // Returns number token
 *}
 */
public final class NumberState implements LexingState {
    private final Source source;
    private final LexingResult result;

    /**
     * Creates a new number state with the given source and accumulated result.
     *
     * @param source The source code positioned at a numeric literal
     * @param result The accumulated lexing result
     */
    public NumberState(final Source source, final LexingResult result) {
        this.source = source;
        this.result = result;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Processes the numeric literal by consuming consecutive digit characters. If a decimal point is encountered
     * followed by at least one digit, continues consuming digits to form a decimal number. Creates a NUMBER token with
     * the complete numeric lexeme.
     */
    @Override
    public LexingStep next() {
        Source curr = this.source;
        StringBuilder lexeme = new StringBuilder();
        while (curr.canPeek(0) && Character.isDigit(curr.peek(0))) {
            lexeme.append(curr.peek(0));
            curr = curr.skip(1);
        }
        if (curr.canPeek(0) && curr.peek(0) == '.'
                && curr.canPeek(1) && Character.isDigit(curr.peek(1))) {
            lexeme.append('.');
            curr = curr.skip(1);
            while (curr.canPeek(0) && Character.isDigit(curr.peek(0))) {
                lexeme.append(curr.peek(0));
                curr = curr.skip(1);
            }
        }
        return new LexingStep(
                new InitialState(
                        curr,
                        this.result.withToken(
                                new ExplicitToken(TokenType.NUMBER, lexeme.toString(), this.source.position())
                        )
                ),
                false
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LexingResult collectResult() {
        return this.result;
    }
}
