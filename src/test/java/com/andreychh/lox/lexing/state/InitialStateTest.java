package com.andreychh.lox.lexing.state;

import com.andreychh.lox.Error;
import com.andreychh.lox.Position;
import com.andreychh.lox.Source;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenFromLexeme;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InitialStateTest {
    @Test
    void shouldTransitionToEOFStateOnEmptySource() {
        LexingState state = new InitialState(new Source(""));

        LexingStep step = state.next();
        assertFalse(step.isFinal());
        assertInstanceOf(EOFState.class, step.state());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\t", "\n", "\r"})
    void shouldSkipWhitespaceAndRemainInInitialState(String whitespace) {
        LexingState state = new InitialState(new Source(whitespace + "a"));

        LexingStep step = state.next();
        assertFalse(step.isFinal());
        assertInstanceOf(InitialState.class, step.state());

        LexingResult result = step.state().collectResult();

        List<Token> tokens = this.toList(result.tokens());
        assertTrue(tokens.isEmpty());

        List<Error> errors = this.toList(result.errors());
        assertTrue(errors.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"(", ")", "{", "}", ".", ",", ";", "+", "-", "*"})
    void shouldCreateTokenForSingleCharOperators(String operator) {
        LexingState state = new InitialState(new Source(operator));
        Token expectedToken = new TokenFromLexeme(operator, new Position(1, 1));

        LexingStep step = state.next();
        assertFalse(step.isFinal());
        assertInstanceOf(InitialState.class, step.state());

        LexingResult result = step.state().collectResult();

        List<Token> tokens = this.toList(result.tokens());
        assertEquals(1, tokens.size());
        assertEquals(expectedToken, tokens.getFirst());

        List<Error> errors = this.toList(result.errors());
        assertTrue(errors.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"!", "=", ">", "<"})
    void shouldTransitionToCompoundOperatorState(String operatorStart) {
        LexingState state = new InitialState(new Source(operatorStart));

        LexingStep step = state.next();
        assertFalse(step.isFinal());
        assertInstanceOf(CompoundOperatorState.class, step.state());

        LexingResult result = step.state().collectResult();

        List<Token> tokens = this.toList(result.tokens());
        assertTrue(tokens.isEmpty());

        List<Error> errors = this.toList(result.errors());
        assertTrue(errors.isEmpty());
    }

    @Test
    void shouldAddErrorAndSkipUnexpectedCharacter() {
        LexingState state = new InitialState(new Source("?a"));

        LexingStep step = state.next();
        assertFalse(step.isFinal());
        assertInstanceOf(InitialState.class, step.state());

        LexingResult result = step.state().collectResult();

        List<Token> tokens = this.toList(result.tokens());
        assertTrue(tokens.isEmpty());

        List<Error> errors = this.toList(result.errors());
        assertEquals(1, errors.size());
    }

    private <T> List<T> toList(final Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }
}