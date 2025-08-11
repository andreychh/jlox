package com.andreychh.lox.lexing.state;

import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;
import com.andreychh.lox.source.Fragment;
import com.andreychh.lox.source.PatternSource;
import com.andreychh.lox.source.Source;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenType;

/**
 * Represents a lexing state for processing numeric literals.
 * <p>
 * This state is entered when a digit character is encountered in the source code.
 * <p>
 * {@snippet :
 * // Creates a number token for "123"
 * NumberState state = new NumberState(new Source("123"), new LexingResult());
 * LexingStep step = state.next(); // Returns NUMBER token
 *
 * // Creates a number token for "123.45"
 * NumberState decimalState = new NumberState(new Source("123.45"), new LexingResult());
 * LexingStep decimalStep = decimalState.next(); // Returns NUMBER token
 *
 * // Creates a number token for "42.0"
 * NumberState zeroDecimalState = new NumberState(new Source("42.0"), new LexingResult());
 * LexingStep zeroStep = zeroDecimalState.next(); // Returns NUMBER token
 *}
 *
 * @apiNote The state that creates {@code NumberState} must guarantee that {@code source.take(1)} returns a digit
 * character.
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
        Fragment taken = this.takeNumber();
        Token token = new ExplicitToken(TokenType.NUMBER, taken.value(), this.source.position());
        LexingState state = new InitialState(taken.remaining(), this.result.withToken(token));
        return new LexingStep(state, false);
    }

    /**
     * Takes a numeric literal from the source, supporting integer and decimal forms.
     *
     * @return Fragment containing the full numeric lexeme and remaining source
     */
    private Fragment takeNumber() {
        Fragment integer = new PatternSource(this.source).take("[0-9]");
        StringBuilder lexeme = new StringBuilder(integer.value());
        Source remaining = integer.remaining();
        if (this.hasFraction(remaining)) {
            Fragment fraction = new PatternSource(remaining.skip(1)).take("[0-9]");
            lexeme.append('.').append(fraction.value());
            remaining = fraction.remaining();
        }
        return new Fragment(lexeme.toString(), remaining);
    }

    /**
     * Checks if the numeric literal has a fractional part (i.e., a dot followed by at least one digit).
     *
     * @param source The source to check for a fraction
     * @return {@code true} if a fraction exists, {@code false} otherwise
     */
    private boolean hasFraction(final Source source) {
        return new PatternSource(source).matches(new String[]{"\\.", "[0-9]"});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LexingResult collectResult() {
        return this.result;
    }
}
