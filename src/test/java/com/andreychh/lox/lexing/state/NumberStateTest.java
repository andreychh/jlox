package com.andreychh.lox.lexing.state;

import com.andreychh.lox.Position;
import com.andreychh.lox.Source;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.TokenType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link NumberState}.
 */
class NumberStateTest {
    @Test
    void createsNumberTokenFromIntegerLiteral() {
        assertEquals(
                new ExplicitToken(TokenType.NUMBER, "42", new Position(1, 1)),
                new NumberState(new Source("42"), new LexingResult())
                        .next()
                        .state()
                        .collectResult()
                        .tokens()
                        .iterator()
                        .next(),
                "NumberState does not create number token from integer literal"
        );
    }

    @Test
    void preservesZeroPaddedFloatLiteral() {
        assertEquals(
                new ExplicitToken(TokenType.NUMBER, "042.000", new Position(1, 1)),
                new NumberState(new Source("042.000"), new LexingResult())
                        .next()
                        .state()
                        .collectResult()
                        .tokens()
                        .iterator()
                        .next(),
                "NumberState incorrectly simplifies the lexeme instead of preserving 042.000"
        );
    }

    @Test
    void createsNumberTokenFromFloatLiteral() {
        assertEquals(
                new ExplicitToken(TokenType.NUMBER, "42.0", new Position(1, 1)),
                new NumberState(new Source("42.0"), new LexingResult())
                        .next()
                        .state()
                        .collectResult()
                        .tokens()
                        .iterator()
                        .next(),
                "NumberState does not create number token from float literal"
        );
    }

    @Test
    void createsNumberTokenFromIntegerWithTrailingDot() {
        assertEquals(
                new ExplicitToken(TokenType.NUMBER, "42", new Position(1, 1)),
                new NumberState(new Source("42."), new LexingResult())
                        .next()
                        .state()
                        .collectResult()
                        .tokens()
                        .iterator()
                        .next(),
                "NumberState does not create number token from integer with trailing dot"
        );
    }

    @Test
    void createsNumberTokenFromMultipleDots() {
        assertEquals(
                new ExplicitToken(TokenType.NUMBER, "1.2", new Position(1, 1)),
                new NumberState(new Source("1.2.3"), new LexingResult())
                        .next()
                        .state()
                        .collectResult()
                        .tokens()
                        .iterator()
                        .next(),
                "NumberState does not create number token from input with multiple dots"
        );
    }

    @Test
    void createsNumberTokenFromDoubleDots() {
        assertEquals(
                new ExplicitToken(TokenType.NUMBER, "1", new Position(1, 1)),
                new NumberState(new Source("1..2.3"), new LexingResult())
                        .next()
                        .state()
                        .collectResult()
                        .tokens()
                        .iterator()
                        .next(),
                "NumberState does not create number token from input with double dots"
        );
    }
}
