package com.andreychh.lox.parsing.expression;

import com.andreychh.lox.parsing.operation.Operation;
import com.andreychh.lox.token.Token;

/**
 * Represents a unary operation expression in the Lox language.
 * <p>
 * A unary expression consists of an operator token and a single operand.
 */
public final class UnaryExpression implements Expression {
    private final Token operator;
    private final Expression operand;

    /**
     * Constructs a unary expression with the given operator and operand.
     *
     * @param operator the operator token
     * @param operand  the operand expression
     */
    public UnaryExpression(final Token operator, final Expression operand) {
        this.operator = operator;
        this.operand = operand;
    }

    /**
     * Applies the given operation to this unary expression.
     *
     * @param operation the operation to apply
     * @param <T>       the result type of the operation
     * @return the result of applying the operation
     */
    @Override
    public <T> T perform(final Operation<T> operation) {
        return operation.applyToUnary(this.operator, this.operand);
    }
}
