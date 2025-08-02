package com.andreychh.lox.lexing;

import com.andreychh.lox.lexing.state.LexingState;

/**
 * Represents the result of a state transition in the FSM.
 *
 * @param state   The next state in the machine.
 * @param isFinal A flag indicating if this transition is the final one.
 */
public record LexingStep(LexingState state, boolean isFinal) {
}
