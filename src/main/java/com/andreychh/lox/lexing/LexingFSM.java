package com.andreychh.lox.lexing;

import com.andreychh.lox.Source;
import com.andreychh.lox.lexing.state.InitialState;
import com.andreychh.lox.lexing.state.LexingState;

public final class LexingFSM {
    private final Source source;

    public LexingFSM(final Source source) {
        this.source = source;
    }

    /**
     * Runs the state machine to tokenize the given source code.
     *
     * @return A {@code LexingResult} containing the list of tokens and any
     * errors found.
     */
    public LexingResult tokenize() {
        LexingState state = new InitialState(this.source);
        while (true) {
            final LexingStep step = state.next();
            if (step.isFinal()) {
                break;
            }
            state = step.state();
        }
        return state.collectResult();
    }
}
