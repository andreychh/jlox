package com.andreychh.lox.parsing.operation;

import com.andreychh.lox.Position;
import com.andreychh.lox.parsing.expression.LiteralExpression;
import com.andreychh.lox.parsing.expression.UnaryExpression;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.TokenType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link PrintOperation}.
 */
final class PrintOperationTest {
    @Test
    void formatsBinaryExpressionAsParenthesizedOperatorAndOperands() {
        assertEquals(
            "(+ 10 20)",
            new PrintOperation().applyToBinary(
                new ExplicitToken(TokenType.PLUS, "+", new Position(1, 2)),
                new LiteralExpression(new ExplicitToken(TokenType.NUMBER, "10", new Position(1, 1))),
                new LiteralExpression(new ExplicitToken(TokenType.NUMBER, "20", new Position(1, 3)))
            ),
            "PrintOperation formats binary expression incorrectly"
        );
    }

    @Test
    void formatsGroupingExpressionAsParenthesizedGrouped() {
        assertEquals(
            "(10)",
            new PrintOperation().applyToGrouping(
                new LiteralExpression(
                    new ExplicitToken(TokenType.NUMBER, "10", new Position(2, 2)
                    )
                )
            ),
            "PrintOperation formats grouping expression incorrectly"
        );
    }

    @Test
    void formatsLiteralExpressionAsLexeme() {
        assertEquals(
            "10",
            new PrintOperation().applyToLiteral(
                new ExplicitToken(TokenType.NUMBER, "10", new Position(3, 3))
            ),
            "PrintOperation formats literal expression incorrectly"
        );
    }

    @Test
    void formatsUnaryExpressionAsParenthesizedOperatorAndOperand() {
        assertEquals(
            "(- 10)",
            new PrintOperation().applyToUnary(
                new ExplicitToken(TokenType.MINUS, "-", new Position(4, 0)),
                new LiteralExpression(
                    new ExplicitToken(TokenType.NUMBER, "10", new Position(4, 4))
                )
            ),
            "PrintOperation formats unary expression incorrectly"
        );
    }

    @Test
    void formatsNestedExpressionsCorrectly() {
        assertEquals(
            "(+ (- 10) 20)",
            new PrintOperation().applyToBinary(
                new ExplicitToken(TokenType.PLUS, "+", new Position(5, 1)),
                new UnaryExpression(
                    new ExplicitToken(TokenType.MINUS, "-", new Position(5, 2)),
                    new LiteralExpression(new ExplicitToken(TokenType.NUMBER, "10", new Position(5, 3)))
                ),
                new LiteralExpression(new ExplicitToken(TokenType.NUMBER, "20", new Position(5, 4)))
            ),
            "PrintOperation formats nested expressions incorrectly"
        );
    }
}
