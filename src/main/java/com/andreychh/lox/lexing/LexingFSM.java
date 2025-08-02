package com.andreychh.lox.lexing;

import com.andreychh.lox.Source;
import com.andreychh.lox.Tokens;

public final class LexingFSM {
    /**
     * Runs the state machine to tokenize the given source code.
     *
     * @param source The source code to scan.
     * @return A {@code LexerResult} containing the list of tokens and any
     * errors found.
     */
    public LexingResult run(final Source source) {
        LexingState state = new InitialState(new Tokens(), source);

        while (true) {
            Transition transition = state.process();
            state = transition.state();

            if (transition.isFinal()) {
                break;
            }
        }

        return state.collect();
    }
}
