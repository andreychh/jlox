package com.andreychh.lox.lexing;


import com.andreychh.lox.Tokens;

/**
 * Represents a state in the Lexing Finite State Machine (FSM).
 */
public interface LexingState {
    /**
     * Processes the current state and returns the next state in the machine.
     */
    LexingState process();

    /**
     * Checks if the state is a terminal one, signaling that the process should stop.
     *
     * @return true if this is a terminal state, false otherwise.
     */
    boolean isTerminal();

    /**
     * Returns the collection of tokens accumulated up to this state.
     */
    Tokens tokens();
}
