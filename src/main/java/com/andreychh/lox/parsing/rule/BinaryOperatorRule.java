package com.andreychh.lox.parsing.rule;

import java.util.function.Supplier;

import com.andreychh.lox.parsing.ParsingStep;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenType;
import com.andreychh.lox.token.stream.ConsumptionResult;
import com.andreychh.lox.token.stream.TokenStream;


/**
 * Left-associative binary operator parsing rule.
 * <p>
 * Parses expressions with binary operators using iterative approach. Handles operator precedence through rule
 * hierarchy.
 */
public final class BinaryOperatorRule implements ParsingRule {
    private final Supplier<ParsingRule> operandRule;
    private final TokenType[][] operatorPattern;

    /**
     * Creates binary operator rule.
     *
     * @param operandRule     supplier for operand parsing rule
     * @param operatorPattern accepted operator token patterns
     */
    public BinaryOperatorRule(final Supplier<ParsingRule> operandRule, final TokenType[][] operatorPattern) {
        this.operandRule = operandRule;
        this.operatorPattern = operatorPattern;
    }

    /**
     * Parses left-associative binary expressions.
     *
     * @param tokens token stream to parse
     * @return parsing step with binary expression tree
     */
    @Override
    public ParsingStep parse(final TokenStream tokens) {
        ParsingStep left = this.operandRule.get().parse(tokens);
        while (left.remaining().lookaheadMatches(this.operatorPattern)) {
            ConsumptionResult consumption = left.remaining().consume(1);
            Token operator = consumption.consumed().get(0);
            ParsingStep right = this.operandRule.get().parse(consumption.remaining());
            left = new ParsingStep(left.report().withBinary(operator, right.report()), right.remaining());
        }
        return left;
    }
}
