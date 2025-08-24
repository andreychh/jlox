package com.andreychh.lox.lexing.state;

import com.andreychh.lox.lexing.LexingResult;

/**
 * Represents the final state indicating that lexical analysis is complete.
 */
public final class TerminatedState implements LexingState {
    private final LexingResult result;

    /**
     * Creates a new terminated state with the final result.
     *
     * @param result The final accumulated lexing result
     */
    public TerminatedState(final LexingResult result) {
        this.result = result;
    }

    /**
     * Throws an exception since this is a final state.
     *
     * @throws IllegalStateException always, as this state should not transition
     */
    @Override
    public LexingState next() {
        throw new IllegalTransitionException("Cannot transition from final state");
    }

    @Override
    public boolean isFinal() {
        return true;
    }

    @Override
    public LexingResult collectResult() {
        return this.result;
    }
}
