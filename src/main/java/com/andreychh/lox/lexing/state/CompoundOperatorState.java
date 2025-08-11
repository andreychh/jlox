package com.andreychh.lox.lexing.state;

import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;
import com.andreychh.lox.source.Fragment;
import com.andreychh.lox.source.Source;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenFromLexeme;

/**
 * Represents a state that handles operators which can be either single or double character tokens.
 * <p>
 * This state processes ambiguous operators such as '!', '=', '>', and '<', which can be followed by an '=' to form
 * compound operators ('!=', '==', '>=', '<='). It looks ahead by one character to resolve this ambiguity and produces
 * the appropriate token.
 * <p>
 * {@snippet :
 * // Example: parsing '!='
 * Source src = new Source("!=");
 * LexingResult result = new LexingResult();
 * CompoundOperatorState state = new CompoundOperatorState(src, result);
 * LexingStep step = state.next(); // Produces token for '!=' and advances source
 *}
 *
 * @apiNote The state that creates {@code CompoundOperatorState} must guarantee that {@code source.take(1)} returns one
 * of the following: {@code "!"}, {@code "="}, {@code ">"}, or {@code "<"}.
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
     * Examines the next character to determine if it forms a compound operator with the current character and creates
     * the appropriate token.
     */
    @Override
    public LexingStep next() {
        Fragment taken = this.takeOperator();
        Token token = new TokenFromLexeme(taken.value(), this.source.position());
        LexingState state = new InitialState(taken.remaining(), this.result.withToken(token));
        return new LexingStep(state, false);
    }

    /**
     * Takes the operator fragment from the source, handling both single and compound forms.
     *
     * @return The fragment representing the operator lexeme and the remaining source
     */
    private Fragment takeOperator() {
        return this.isCompound() ? this.source.take(2) : this.source.take(1);
    }

    /**
     * Checks if the current operator is a compound operator (e.g., '!=', '==', '>=', '<=').
     *
     * @return {@code true} if the next character is '=', {@code false} otherwise
     */
    private boolean isCompound() {
        return this.source.hasNext(2) && this.source.peek(1).equals("=");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LexingResult collectResult() {
        return this.result;
    }
}
