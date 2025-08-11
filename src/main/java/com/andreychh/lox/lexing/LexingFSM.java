package com.andreychh.lox.lexing;

import com.andreychh.lox.lexing.state.InitialState;
import com.andreychh.lox.lexing.state.LexingState;
import com.andreychh.lox.source.Source;

/**
 * The main Finite State Machine (FSM) for lexical analysis.
 * <p>
 * This class orchestrates the state transitions of the lexical analysis
 * process, starting from the initial state and continuing until a terminal
 * state is reached.
 * It collects and returns the final result containing all tokens and errors.
 */
public final class LexingFSM {
    private final Source source;

    /**
     * Creates a new lexical analysis FSM for the given source.
     *
     * @param source The source code to analyze
     */
    public LexingFSM(final Source source) {
        this.source = source;
    }

    /**
     * Runs the state machine to tokenize the source code.
     * <p>
     * This method drives the state transitions until a final state is reached,
     * then returns the collected result.
     *
     * @return A {@link LexingResult} containing all tokens and any errors found
     */
    public LexingResult tokenize() {
        LexingState state = new InitialState(this.source);
        while (true) {
            LexingStep step = state.next();
            if (step.isFinal()) {
                break;
            }
            state = step.state();
        }
        return state.collectResult();
    }
}
