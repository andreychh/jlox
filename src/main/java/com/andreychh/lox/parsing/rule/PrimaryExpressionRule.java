package com.andreychh.lox.parsing.rule;

import java.util.function.Supplier;

import com.andreychh.lox.error.Errors;
import com.andreychh.lox.parsing.ParsingReport;
import com.andreychh.lox.parsing.ParsingStep;
import com.andreychh.lox.parsing.expression.LiteralExpression;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenType;
import com.andreychh.lox.token.stream.ConsumptionResult;
import com.andreychh.lox.token.stream.TokenStream;

/**
 * Primary expression parsing rule for literals and groupings.
 * <p>
 * Handles base cases in expression grammar: literals and parenthesized expressions.
 */
public final class PrimaryExpressionRule implements ParsingRule {
    private final Supplier<ParsingRule> subexpressionRule;

    /**
     * Creates primary expression rule.
     *
     * @param subexpressionRule supplier for parsing subexpressions in parentheses
     */
    public PrimaryExpressionRule(final Supplier<ParsingRule> subexpressionRule) {
        this.subexpressionRule = subexpressionRule;
    }

    /**
     * Parses literals or grouped expressions.
     *
     * @param stream token stream to parse
     * @return parsing step with literal or grouped expression
     */
    @Override
    public ParsingStep parse(final TokenStream stream) {
        if (!stream.canLookahead(1)) {
            throw new RuntimeException("Unexpected end of input");
        }
        if (stream.lookaheadMatches(new TokenType[][]{
            {TokenType.FALSE, TokenType.TRUE, TokenType.NIL, TokenType.NUMBER, TokenType.STRING}
        })) {
            ConsumptionResult consumption = stream.consume(1);
            Token literal = consumption.consumed().get(0);
            ParsingReport report = new ParsingReport(new LiteralExpression(literal), new Errors());
            return new ParsingStep(report, consumption.remaining());
        }
        if (stream.lookaheadMatches(new TokenType[][]{{TokenType.LEFT_PAREN}})) {
            ParsingStep subexpr = this.subexpressionRule.get().parse(stream.advance(1));
            if (!subexpr.remaining().lookaheadMatches(new TokenType[][]{{TokenType.RIGHT_PAREN}})) {
                throw new RuntimeException("Expected right parenthesis after grouping expression");
            }
            return new ParsingStep(subexpr.report().withGrouping(), subexpr.remaining().advance(1));
        }
        throw new RuntimeException("Expected primary expression");
    }
}
