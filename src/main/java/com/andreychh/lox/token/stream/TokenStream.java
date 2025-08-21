package com.andreychh.lox.token.stream;

import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenType;

/**
 * Represents a stream of tokens for parsing.
 *
 * <p>
 * This interface provides methods for lookahead, consumption, and advancement of tokens.
 */
public interface TokenStream {
    /**
     * Checks if the stream can look ahead by the specified offset.
     *
     * @param offset the number of tokens to look ahead
     * @return {@code true} if lookahead is possible, otherwise {@code false}
     */
    boolean canLookahead(int offset);

    /**
     * Returns the token at the specified lookahead offset.
     *
     * @param offset the number of tokens to look ahead
     * @return the token at the given offset
     */
    Token lookahead(int offset);

    /**
     * Consumes the specified number of tokens and returns a fragment containing the consumed tokens and the remaining
     * stream.
     *
     * @param count the number of tokens to consume
     * @return a {@link Fragment} with consumed tokens and the remaining stream
     */
    Fragment consume(int count);

    /**
     * Returns a new stream advanced by the specified number of tokens.
     *
     * @param count the number of tokens to advance
     * @return a new {@code TokenStream} advanced by {@code count} tokens
     */
    TokenStream advance(int count);

    /**
     * Checks if the lookahead tokens match the expected types.
     *
     * @param expected an array of arrays of expected token types for each lookahead position
     * @return {@code true} if all lookahead tokens match the expected types, otherwise {@code false}
     */
    boolean lookaheadMatches(TokenType[][] expected);
}
