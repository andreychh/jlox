package com.andreychh.lox.parsing;

import com.andreychh.lox.error.Errors;
import com.andreychh.lox.parsing.expression.BinaryExpression;
import com.andreychh.lox.parsing.expression.Expression;
import com.andreychh.lox.parsing.expression.GroupingExpression;
import com.andreychh.lox.parsing.expression.UnaryExpression;
import com.andreychh.lox.token.Token;

/**
 * Represents parsing result with expression and errors.
 * <p>
 * Collects all parsing information without throwing exceptions immediately.
 * <p>
 * Usage:
 * {@snippet :
 * ParsingReport report = new ParsingReport(expression, errors);
 * ParsingReport binary = report.withBinary(operator, rightReport);
 * ParsingReport unary = report.withUnary(operator);
 * ParsingReport grouped = report.withGrouping();
 *}
 */
public final class ParsingReport {
    private final Expression expression;
    private final Errors errors;

    /**
     * Creates parsing report.
     *
     * @param expression parsed expression
     * @param errors     parsing errors
     */
    public ParsingReport(final Expression expression, final Errors errors) {
        this.expression = expression;
        this.errors = errors;
    }

    /**
     * Wraps expression in binary operation.
     *
     * @param operator binary operator token
     * @param right    right operand report
     * @return new report with binary expression
     */
    public ParsingReport withBinary(final Token operator, final ParsingReport right) {
        Expression binary = new BinaryExpression(operator, this.expression, right.expression);
        return new ParsingReport(binary, this.errors.withErrors(right.errors));
    }

    /**
     * Wraps expression in unary operation.
     *
     * @param operator unary operator token
     * @return new report with unary expression
     */
    public ParsingReport withUnary(final Token operator) {
        Expression unary = new UnaryExpression(operator, this.expression);
        return new ParsingReport(unary, this.errors);
    }

    /**
     * Wraps expression in grouping parentheses.
     *
     * @return new report with grouped expression
     */
    public ParsingReport withGrouping() {
        Expression grouped = new GroupingExpression(this.expression);
        return new ParsingReport(grouped, this.errors);
    }
}
