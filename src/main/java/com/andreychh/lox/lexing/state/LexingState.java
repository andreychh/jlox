package com.andreychh.lox.lexing.state;

import com.andreychh.lox.lexing.LexingResult;

/**
 * Represents a state in the Lexical Analysis Finite State Machine (FSM).
 * <p>
 * Each state handles input characters, determines transitions to other states, and produces tokens when needed. Check
 * {@link #isFinal()} before calling {@link #next()} to avoid exceptions.
 *
 * @apiNote States that add tokens or errors to {@link LexingResult} must advance the
 * {@link com.andreychh.lox.source.Source}
 */
public interface LexingState {
    /**
     * Transitions to the next state.
     *
     * @return The next state
     * @throws IllegalStateException if called on a final state
     */
    LexingState next();

    /**
     * Checks if this state is final in the lexing process.
     *
     * @return {@code true} if final, {@code false} otherwise
     */
    boolean isFinal();

    /**
     * Returns the lexing result accumulated so far.
     *
     * @return All tokens and errors collected up to this state
     */
    LexingResult collectResult();
}
