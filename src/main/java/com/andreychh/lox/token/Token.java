package com.andreychh.lox.token;

/**
 * Represents a lexical token in the source code.
 */
public interface Token {
    /**
     * Checks if the token's type matches any of the expected types.
     *
     * @param expected an array of expected token types
     * @return {@code true} if the token's type is present in the expected array, otherwise {@code false}
     */
    boolean hasAnyType(TokenType[] expected);

    /**
     * Returns the lexeme (textual representation) of this token.
     *
     * @return the lexeme of the token
     */
    String lexeme();
}
