package com.andreychh.lox.token.stream;

import java.util.List;

import com.andreychh.lox.token.Token;

/**
 * Represents a fragment of consumed tokens and the remaining token stream.
 * <p>
 * Example usage:
 * {@snippet :
 * Fragment fragment = stream.consume(2);
 * List<Token> consumed = fragment.consumed();
 * TokenStream remaining = fragment.remaining();
 *}
 *
 * @param consumed  the list of tokens that were consumed
 * @param remaining the remaining {@link ListTokenStream} after consumption
 */
public record Fragment(List<Token> consumed, ListTokenStream remaining) {
}
