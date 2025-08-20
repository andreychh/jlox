package com.andreychh.lox.parsing.expression;

import com.andreychh.lox.parsing.operation.Operation;

/**
 * Represents an abstract syntax tree node for an expression in the Lox language.
 */
public interface Expression {
    /**
     * Applies the given operation to this expression node.
     *
     * @param operation the operation to apply
     * @param <T>       the result type of the operation
     * @return the result of applying the operation
     */
    <T> T perform(Operation<T> operation);
}
