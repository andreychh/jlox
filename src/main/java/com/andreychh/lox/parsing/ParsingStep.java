package com.andreychh.lox.parsing;

import com.andreychh.lox.token.stream.TokenStream;

/**
 * Single parsing step with result and remaining tokens.
 * <p>
 * Usage:
 * {@snippet :
 * ParsingStep step = rule.parse(tokens);
 * ParsingReport result = step.report();
 * TokenStream remaining = step.remaining();
 *}
 */
public record ParsingStep(ParsingReport report, TokenStream remaining) {
}
