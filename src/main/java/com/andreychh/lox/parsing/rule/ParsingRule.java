package com.andreychh.lox.parsing.rule;

import com.andreychh.lox.parsing.ParsingStep;
import com.andreychh.lox.token.stream.TokenStream;

/**
 * Grammar rule for parsing token streams.
 */
public interface ParsingRule {
    /**
     * Parses tokens according to grammar rule.
     *
     * @param stream token stream to parse
     * @return parsing step with result and remaining tokens
     */
    ParsingStep parse(TokenStream stream);
}
