package com.andreychh.lox.lexing.state;

import com.andreychh.lox.Position;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenFromLexeme;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TerminatedStateTest {
    @Test
    void shouldSignalTerminationAndPreserveFinalResult() {
        Token existingToken = new TokenFromLexeme("var", new Position(1, 1));
        LexingResult expectedResult = new LexingResult().withToken(existingToken);
        LexingState state = new TerminatedState(expectedResult);

        LexingStep step = state.next();
        assertTrue(step.isFinal());
        assertSame(state, step.state());

        LexingResult result = state.collectResult();
        assertSame(expectedResult, result);
    }
}
