package com.andreychh.lox.lexing.state;

import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.source.Fragment;
import com.andreychh.lox.source.PatternSource;
import com.andreychh.lox.source.Source;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenType;

/**
 * Represents a state that processes numeric literals (integers and decimals).
 *
 * @apiNote Expects {@code source.take(1)} to return a digit character
 */
public final class NumberState implements LexingState {
    private final Source source;
    private final LexingResult result;

    /**
     * Creates a new number state.
     *
     * @param source The source code positioned at a numeric literal
     * @param result The accumulated lexing result
     */
    public NumberState(final Source source, final LexingResult result) {
        this.source = source;
        this.result = result;
    }

    /**
     * Consumes the numeric literal and creates a NUMBER token.
     */
    @Override
    public LexingState next() {
        Fragment taken = this.takeNumber();
        Token token = new ExplicitToken(TokenType.NUMBER, taken.value(), this.source.position());
        return new InitialState(taken.remaining(), this.result.withToken(token));
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
     * Checks if the numeric literal has a fractional part (dot followed by digits).
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
    public boolean isFinal() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LexingResult collectResult() {
        return this.result;
    }
}
