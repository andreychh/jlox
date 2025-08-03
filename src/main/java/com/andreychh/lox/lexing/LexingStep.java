package com.andreychh.lox.lexing;

import com.andreychh.lox.lexing.state.LexingState;

/**
 * Represents the result of a state transition in the Finite State Machine.
 * <p>
 * This record combines the next state to transition to and a flag indicating
 * whether the FSM should terminate after this step.
 *
 * @param state   The next state in the lexing process
 * @param isFinal A flag indicating if this is the final transition
 */
public record LexingStep(LexingState state, boolean isFinal) {
}
