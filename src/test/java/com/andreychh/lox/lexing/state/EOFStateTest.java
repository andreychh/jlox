package com.andreychh.lox.lexing.state;

import com.andreychh.lox.Error;
import com.andreychh.lox.Position;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;
import com.andreychh.lox.source.Source;
import com.andreychh.lox.source.TextSource;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EOFStateTest {
    @Test
    void shouldAppendEOFTokenAndTransitionToTerminatedState() {
        Source source = new TextSource("var").skip(3);
        Token existingToken = new ExplicitToken(TokenType.VAR, "var", new Position(1, 1));
        LexingState state = new EOFState(source, new LexingResult().withToken(existingToken));
        Token expectedToken = new ExplicitToken(TokenType.EOF, "", source.position());

        LexingStep step = state.next();
        assertFalse(step.isFinal());
        assertInstanceOf(TerminatedState.class, step.state());

        LexingResult result = step.state().collectResult();

        List<Token> tokens = this.toList(result.tokens());
        assertEquals(2, tokens.size());
        assertEquals(existingToken, tokens.get(0));
        assertEquals(expectedToken, tokens.get(1));

        List<Error> errors = this.toList(result.errors());
        assertEquals(0, errors.size());
    }

    private <T> List<T> toList(final Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }
}
