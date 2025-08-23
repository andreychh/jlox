package com.andreychh.lox.lexing.state;

/**
 * Represents an illegal state transition in the lexical analysis FSM.
 * <p>
 * This exception is thrown when an attempt is made to perform an invalid transition, such as calling
 * {@link LexingState#next()} on a final state. This typically indicates a programming error in the FSM logic or
 * incorrect usage of the state API.
 */
public final class IllegalTransitionException extends RuntimeException {
    /**
     * Creates a new illegal transition exception with the specified message.
     *
     * @param message The detail message explaining the illegal transition
     */
    public IllegalTransitionException(final String message) {
        super(message);
    }
}
