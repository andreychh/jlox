package com.andreychh.lox.token;

/**
 * Defines the contract for lexical tokens in the Lox language.
 * <p>
 * Tokens represent the smallest meaningful units in the source code,
 * such as keywords, identifiers, operators, and literals.
 */
public interface Token {
    /**
     * Returns a formatted string representation of the token.
     *
     * @return A string representation of the token
     */
    String format();
}
