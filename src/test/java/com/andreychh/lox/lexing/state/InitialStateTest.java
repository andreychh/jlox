package com.andreychh.lox.lexing.state;

import com.andreychh.lox.Position;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.source.TextSource;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.TokenType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * Tests for {@link InitialState}.
 */
final class InitialStateTest {
    @ParameterizedTest
    @MethodSource("singleCharOperatorCases")
    void createsTokenForSingleCharacterOperator(String operator, TokenType expectedType) {
        assertEquals(
            new ExplicitToken(expectedType, operator, new Position(1, 1)),
            new InitialState(new TextSource(operator), new LexingResult())
                .next()
                .collectResult()
                .tokens()
                .get(0),
            "InitialState did not create correct token for single char operator"
        );
    }

    static Stream<Arguments> singleCharOperatorCases() {
        return Stream.of(
            Arguments.of("(", TokenType.LEFT_PAREN),
            Arguments.of(")", TokenType.RIGHT_PAREN),
            Arguments.of("{", TokenType.LEFT_BRACE),
            Arguments.of("}", TokenType.RIGHT_BRACE),
            Arguments.of(".", TokenType.DOT),
            Arguments.of(",", TokenType.COMMA),
            Arguments.of(";", TokenType.SEMICOLON),
            Arguments.of("+", TokenType.PLUS),
            Arguments.of("-", TokenType.MINUS),
            Arguments.of("*", TokenType.STAR)
        );
    }

    @Test
    void transitionsToEOFStateWhenSourceIsEmpty() {
        assertInstanceOf(
            EOFState.class,
            new InitialState(new TextSource(""), new LexingResult()).next(),
            "InitialState did not transition to EOFState on empty source"
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\t", "\n", "\r"})
    void remainsInInitialStateAfterWhitespace(String whitespace) {
        assertInstanceOf(
            InitialState.class,
            new InitialState(new TextSource(whitespace + "a"), new LexingResult()).next(),
            "InitialState did not remain in InitialState after skipping whitespace"
        );
    }


    @ParameterizedTest
    @ValueSource(strings = {"!", "=", ">", "<"})
    void transitionsToCompoundOperatorStateForCompoundOperatorStart(String operatorStart) {
        assertInstanceOf(
            CompoundOperatorState.class,
            new InitialState(new TextSource(operatorStart), new LexingResult()).next(),
            "InitialState did not transition to CompoundOperatorState for compound operator start"
        );
    }

    @Test
    void addsErrorAndSkipsUnexpectedCharacter() {
        assertEquals(
            1,
            new InitialState(new TextSource("?a"), new LexingResult())
                .next()
                .collectResult()
                .errors()
                .size(),
            "InitialState did not add error for unexpected character"
        );
    }
}
