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

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link CompoundOperatorState}.
 */
final class CompoundOperatorStateTest {
    @ParameterizedTest()
    @MethodSource("compoundOperatorCases")
    void createsCompoundToken(String input, TokenType expectedType) {
        assertEquals(
            new ExplicitToken(expectedType, input, new Position(1, 1)),
            new CompoundOperatorState(new TextSource(input), new LexingResult())
                .next()
                .collectResult()
                .tokens()
                .get(0),
            "CompoundOperatorState failed to create compound token"
        );
    }

    static Stream<Arguments> compoundOperatorCases() {
        return Stream.of(
            Arguments.of("==", TokenType.EQUAL_EQUAL),
            Arguments.of("!=", TokenType.BANG_EQUAL),
            Arguments.of(">=", TokenType.GREATER_EQUAL),
            Arguments.of("<=", TokenType.LESS_EQUAL)
        );
    }

    @ParameterizedTest()
    @MethodSource("singleOperatorWithTailCases")
    void createsSingleTokenWhenFollowedByAnotherChar(String input, TokenType expectedType) {
        assertEquals(
            new ExplicitToken(expectedType, input.substring(0, 1), new Position(1, 1)),
            new CompoundOperatorState(new TextSource(input), new LexingResult())
                .next()
                .collectResult()
                .tokens()
                .get(0),
            "CompoundOperatorState failed to create single token followed by another char"
        );
    }

    static Stream<Arguments> singleOperatorWithTailCases() {
        return Stream.of(
            Arguments.of("!a", TokenType.BANG),
            Arguments.of(">d", TokenType.GREATER),
            Arguments.of("<c", TokenType.LESS),
            Arguments.of("=d", TokenType.EQUAL)
        );
    }

    @ParameterizedTest()
    @MethodSource("singleOperatorAtEndCases")
    void createsSingleTokenAtEndOfSource(String input, TokenType expectedType) {
        assertEquals(
            new ExplicitToken(expectedType, input, new Position(1, 1)),
            new CompoundOperatorState(new TextSource(input), new LexingResult())
                .next()
                .collectResult()
                .tokens()
                .get(0),
            "CompoundOperatorState failed to create single token at end of source"
        );
    }

    static Stream<Arguments> singleOperatorAtEndCases() {
        return Stream.of(
            Arguments.of("!", TokenType.BANG),
            Arguments.of(">", TokenType.GREATER),
            Arguments.of("<", TokenType.LESS),
            Arguments.of("=", TokenType.EQUAL)
        );
    }

    @Test
    void throwsExceptionWhenUnknownLexemeIsProvided() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new CompoundOperatorState(new TextSource("?"), new LexingResult())
                .next(),
            "CompoundOperatorState does not throw IllegalArgumentException for unknown lexeme"
        );
    }
}
