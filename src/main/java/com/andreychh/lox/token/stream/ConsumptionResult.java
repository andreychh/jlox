package com.andreychh.lox.token.stream;

import java.util.List;

import com.andreychh.lox.token.Token;

/**
 * Represents the report of consuming tokens from a token stream.
 * <p>
 * This record encapsulates both the tokens that were consumed and the remaining portion of the stream after the
 * consumption operation.
 * <p>
 * Example usage:
 * {@snippet :
 * ConsumptionResult consumption = stream.consume(2);
 * List<Token> consumed = consumption.consumed();
 * TokenStream remaining = consumption.remaining();
 *}
 *
 * @param consumed  the list of tokens that were consumed from the stream
 * @param remaining the remaining {@link TokenStream} after consumption
 */
public record ConsumptionResult(List<Token> consumed, TokenStream remaining) {
}
