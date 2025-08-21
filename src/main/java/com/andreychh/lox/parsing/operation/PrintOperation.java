package com.andreychh.lox.parsing.operation;

import com.andreychh.lox.parsing.expression.Expression;
import com.andreychh.lox.token.Token;

/**
 * Implements the {@link Operation} interface to produce a string representation of Lox expressions.
 * <p>
 * This operation traverses the expression tree and formats each node as a string.
 */
public final class PrintOperation implements Operation<String> {
    /**
     * {@inheritDoc}
     * <p>
     * Formats a binary expression as {@code (operator left right)}.
     */
    @Override
    public String applyToBinary(final Token operator, final Expression left, final Expression right) {
        return "(%s %s %s)".formatted(operator.lexeme(), left.perform(this), right.perform(this));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Formats a grouping expression as {@code (grouped)}.
     */
    @Override
    public String applyToGrouping(final Expression grouped) {
        return "(%s)".formatted(grouped.perform(this));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Formats a literal expression using the token's lexeme.
     */
    @Override
    public String applyToLiteral(final Token literal) {
        return literal.lexeme();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Formats a unary expression as {@code (operator operand)}.
     */
    @Override
    public String applyToUnary(final Token operator, final Expression operand) {
        return "(%s %s)".formatted(operator.lexeme(), operand.perform(this));
    }
}
