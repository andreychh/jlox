package com.andreychh.lox.parsing.expression;

import com.andreychh.lox.parsing.operation.Operation;
import com.andreychh.lox.token.Token;

/**
 * Represents a literal value expression in the Lox language.
 * <p>
 * A literal expression holds a token representing a value such as a number, string, or boolean.
 */
public final class LiteralExpression implements Expression {
    private final Token literal;

    /**
     * Constructs a literal expression with the given token.
     *
     * @param literal the literal token
     */
    public LiteralExpression(final Token literal) {
        this.literal = literal;
    }

    /**
     * Applies the given operation to this literal expression.
     *
     * @param operation the operation to apply
     * @param <T>       the result type of the operation
     * @return the result of applying the operation
     */
    @Override
    public <T> T perform(final Operation<T> operation) {
        return operation.applyToLiteral(this.literal);
    }
}
