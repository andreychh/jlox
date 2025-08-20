package com.andreychh.lox.lexing.state;

import com.andreychh.lox.Position;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TerminatedStateTest {
    @Test
    void shouldSignalTerminationAndPreserveFinalResult() {
        Token existingToken = new ExplicitToken(TokenType.VAR, "var", new Position(1, 1));
        LexingResult expectedResult = new LexingResult().withToken(existingToken);
        LexingState state = new TerminatedState(expectedResult);

        LexingStep step = state.next();
        assertTrue(step.isFinal());
        assertSame(state, step.state());

        LexingResult result = state.collectResult();
        assertSame(expectedResult, result);
    }
}
