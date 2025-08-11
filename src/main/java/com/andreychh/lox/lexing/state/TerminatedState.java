package com.andreychh.lox.lexing.state;

import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;

/**
 * Represents the final state of the lexical analysis process.
 * <p>
 * Once the lexer reaches this state, it indicates that the analysis is complete and no further processing should occur.
 * This state always signals that the FSM should terminate when asked for the next step.
 */
public final class TerminatedState implements LexingState {
    private final LexingResult result;

    /**
     * Creates a new terminated state with the final lexing result.
     *
     * @param result The final accumulated lexing result
     */
    public TerminatedState(final LexingResult result) {
        this.result = result;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Always returns a step with the isFinal flag set to {@code true}, indicating that the lexer should terminate.
     */
    @Override
    public LexingStep next() {
        return new LexingStep(this, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LexingResult collectResult() {
        return this.result;
    }
}
