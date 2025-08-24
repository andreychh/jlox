package com.andreychh.lox.lexing;

import com.andreychh.lox.lexing.state.InitialState;
import com.andreychh.lox.lexing.state.LexingState;
import com.andreychh.lox.source.Source;

/**
 * Represents the finite state machine (FSM) that performs lexical analysis of source code.
 * <p>
 * This class serves as the entry point for tokenizing source code. It manages the state transitions from the initial
 * state through various specialized states until reaching a final state, collecting tokens and errors along the way.
 * <p>
 * The FSM handles all types of lexical elements including identifiers, keywords, operators, literals, comments, and
 * whitespace. When invalid characters are encountered, appropriate errors are generated and the analysis continues.
 */
public final class LexingFSM {
    private final Source source;

    /**
     * Creates a new lexical analysis FSM for the given source code.
     *
     * @param source The source code to be tokenized
     */
    public LexingFSM(final Source source) {
        this.source = source;
    }

    /**
     * Executes the finite state machine to perform complete lexical analysis.
     * <p>
     * Starting from the initial state, this method drives the FSM through successive state transitions until a final
     * state is reached. Each state processes characters from the source and may produce tokens or errors.
     *
     * @return The complete lexical analysis result containing all discovered tokens and any errors
     */
    public LexingResult tokenize() {
        LexingState state = new InitialState(this.source, new LexingResult());
        while (!state.isFinal()) {
            state = state.next();
        }
        return state.collectResult();
    }
}
