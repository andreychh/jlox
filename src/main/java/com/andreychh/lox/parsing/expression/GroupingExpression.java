package com.andreychh.lox.parsing.expression;

import com.andreychh.lox.parsing.operation.Operation;

/**
 * Represents a grouping expression in the Lox language.
 * <p>
 * A grouping expression wraps another expression in parentheses to override precedence.
 */
public final class GroupingExpression implements Expression {
    private final Expression grouped;

    /**
     * Constructs a grouping expression with the given inner expression.
     *
     * @param grouped the inner expression to group
     */
    public GroupingExpression(final Expression grouped) {
        this.grouped = grouped;
    }

    /**
     * Applies the given operation to this grouping expression.
     *
     * @param operation the operation to apply
     * @param <T>       the result type of the operation
     * @return the result of applying the operation
     */
    @Override
    public <T> T perform(final Operation<T> operation) {
        return operation.applyToGrouping(this.grouped);
    }
}
