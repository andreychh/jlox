package com.andreychh.lox.lexing;


/**
 * Represents a state in the Lexing Finite State Machine (FSM).
 */
public interface LexingState {
    /**
     * Processes the current state and returns a transition, which contains
     * the next state and a flag indicating if the process should stop.
     * <p>
     * todo: rename
     */
    Transition process();

    /**
     * Returns the result of the computation accumulated up to this state.
     */
    LexingResult collect();
}
