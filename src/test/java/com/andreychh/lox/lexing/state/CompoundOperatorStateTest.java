package com.andreychh.lox.lexing.state;

import com.andreychh.lox.Error;
import com.andreychh.lox.Position;
import com.andreychh.lox.Source;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenFromLexeme;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompoundOperatorStateTest {
    @Test
    void shouldCreateCompoundTokenWhenFollowedByEquals() {
        LexingState state = new CompoundOperatorState(new Source("!="), new LexingResult());
        Token expectedToken = new TokenFromLexeme("!=", new Position(1, 1));

        LexingStep step = state.next();
        assertFalse(step.isFinal());
        assertInstanceOf(InitialState.class, step.state());

        LexingResult result = step.state().collectResult();

        List<Token> tokens = this.toList(result.tokens());
        assertEquals(1, tokens.size());
        assertEquals(expectedToken, tokens.getFirst());

        List<Error> errors = this.toList(result.errors());
        assertEquals(0, errors.size());
    }

    @Test
    void shouldCreateSingleTokenWhenNotFollowedByEquals() {
        LexingState state = new CompoundOperatorState(new Source("!a"), new LexingResult());
        Token expectedToken = new TokenFromLexeme("!", new Position(1, 1));

        LexingStep step = state.next();
        assertFalse(step.isFinal());
        assertInstanceOf(InitialState.class, step.state());

        LexingResult result = step.state().collectResult();

        List<Token> tokens = this.toList(result.tokens());
        assertEquals(1, tokens.size());
        assertEquals(expectedToken, tokens.getFirst());

        List<Error> errors = this.toList(result.errors());
        assertEquals(0, errors.size());
    }

    @Test
    void shouldCreateSingleTokenWhenItIsTheLastCharInSource() {
        LexingState state = new CompoundOperatorState(new Source("!"), new LexingResult());
        Token expectedToken = new TokenFromLexeme("!", new Position(1, 1));

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

    private <T> List<T> toList(final Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }
}
