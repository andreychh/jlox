package com.andreychh.lox.parsing.operation;

import com.andreychh.lox.parsing.expression.*;
import com.andreychh.lox.token.Token;

/**
 * Represents an operation that can be performed on expression nodes in the Lox abstract syntax tree.
 * <p>
 * Implementations of this interface define how to process each kind of expression node.
 *
 * @param <T> the result type of the operation
 */
public interface Operation<T> {
    /**
     * Applies this operation to a binary expression node.
     *
     * @param operator the operator token
     * @param left     the left operand expression
     * @param right    the right operand expression
     * @return the result of the operation
     */
    T applyToBinary(Token operator, Expression left, Expression right);

    /**
     * Applies this operation to a grouping expression node.
     *
     * @param grouped the grouped expression
     * @return the result of the operation
     */
    T applyToGrouping(Expression grouped);

    /**
     * Applies this operation to a literal expression node.
     *
     * @param literal the literal token
     * @return the result of the operation
     */
    T applyToLiteral(Token literal);

    /**
     * Applies this operation to a unary expression node.
     *
     * @param operator the operator token
     * @param operand  the operand expression
     * @return the result of the operation
     */
    T applyToUnary(Token operator, Expression operand);
}
