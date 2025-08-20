package com.andreychh.lox.parsing.expression;

import com.andreychh.lox.parsing.operation.Operation;
import com.andreychh.lox.token.Token;

/**
 * Represents a binary operation expression in the Lox language.
 * <p>
 * A binary expression consists of a left and right operand and an operator token.
 */
public final class BinaryExpression implements Expression {
    private final Token operator;
    private final Expression left;
    private final Expression right;

    /**
     * Constructs a binary expression with the given operator and operands.
     *
     * @param operator the operator token
     * @param left     the left operand
     * @param right    the right operand
     */
    public BinaryExpression(final Token operator, final Expression left, final Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    /**
     * Applies the given operation to this binary expression.
     *
     * @param operation the operation to apply
     * @param <T>       the result type of the operation
     * @return the result of applying the operation
     */
    @Override
    public <T> T perform(final Operation<T> operation) {
        return operation.applyToBinary(this.operator, this.left, this.right);
    }
}
