package com.andreychh.lox.lexing.state;

import com.andreychh.lox.lexing.LexingResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link TerminatedState}.
 */
final class TerminatedStateTest {
    @Test
    void terminatedStateThrowsOnNext() {
        assertThrows(
            IllegalTransitionException.class,
            () -> new TerminatedState(new LexingResult())
                .next(),
            "TerminatedState did not throw when next() was called"
        );
    }
}
