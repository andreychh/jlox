package com.andreychh.lox.lexing.state;

import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;

/**
 * Defines the contract for states in the Lexical Analysis Finite State Machine
 * (FSM).
 * <p>
 * Each state represents a distinct phase in the lexical analysis process and
 * encapsulates the logic for handling input characters at that stage. States
 * are responsible for determining transitions between each other and producing
 * tokens when appropriate.
 */
public interface LexingState {
    /**
     * Processes the current state and determines the next transition.
     *
     * @return A {@link LexingStep} containing the next state and a flag
     * indicating if the lexing process should terminate
     */
    LexingStep next();

    /**
     * Returns the accumulated result of the lexical analysis up to this state.
     *
     * @return The current {@link LexingResult} containing all tokens and errors
     */
    LexingResult collectResult();
}
