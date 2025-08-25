package com.andreychh.lox.parsing.rule;

import java.util.function.Supplier;

import com.andreychh.lox.parsing.ParsingStep;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenType;
import com.andreychh.lox.token.stream.ConsumptionResult;
import com.andreychh.lox.token.stream.TokenStream;

/**
 * Right-associative unary operator parsing rule.
 * <p>
 * Handles prefix unary operators through recursive descent. Falls back to subexpression rule when no operator matches.
 */
public final class UnaryOperatorRule implements ParsingRule {
    private final Supplier<ParsingRule> subexpressionRule;
    private final TokenType[][] operatorPattern;

    /**
     * Creates unary operator rule.
     *
     * @param subexpressionRule supplier for parsing operand expressions
     * @param operatorPattern   accepted unary operator token patterns
     */
    public UnaryOperatorRule(final Supplier<ParsingRule> subexpressionRule, final TokenType[][] operatorPattern) {
        this.subexpressionRule = subexpressionRule;
        this.operatorPattern = operatorPattern;
    }

    /**
     * Parses unary expressions or delegates to subexpression rule.
     *
     * @param stream token stream to parse
     * @return parsing step with unary expression or subexpression result
     */
    @Override
    public ParsingStep parse(final TokenStream stream) {
        if (!stream.lookaheadMatches(this.operatorPattern)) {
            return this.subexpressionRule.get().parse(stream);
        }
        ConsumptionResult consumption = stream.consume(1);
        Token operator = consumption.consumed().get(0);
        ParsingStep operandStep = this.parse(consumption.remaining());
        return new ParsingStep(operandStep.report().withUnary(operator), operandStep.remaining());
    }
}
