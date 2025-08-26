package com.andreychh.lox.token.stream;

import java.util.List;
import java.util.stream.IntStream;

import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenType;

/**
 * An implementation of {@link TokenStream} backed by a list of tokens.
 * <p>
 * Example usage:
 * {@snippet :
 * List<Token> tokens = ...;
 * TokenStream stream = new ListTokenStream(tokens);
 * if (stream.canLookahead(1)) {
 *     Token token = stream.lookahead(1);
 * }
 *}
 */
public final class ListTokenStream implements TokenStream {
    private final List<Token> tokens;
    private final int offset;

    /**
     * Constructs a new {@code ListTokenStream} with the given tokens and offset.
     *
     * @param tokens the list of tokens
     * @param offset the starting offset in the token list
     */
    private ListTokenStream(final List<Token> tokens, final int offset) {
        this.tokens = tokens;
        this.offset = offset;
    }

    /**
     * Constructs a new {@code ListTokenStream} starting at offset 0.
     *
     * @param tokens the list of tokens
     */
    public ListTokenStream(final List<Token> tokens) {
        this(tokens, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canLookahead(final int offset) {
        return this.offset + offset <= this.tokens.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Token lookahead(final int offset) {
        return this.tokens.get(this.offset + offset);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConsumptionResult consume(final int count) {
        return new ConsumptionResult(this.tokens.subList(this.offset, this.offset + count), this.advance(count));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListTokenStream advance(final int count) {
        return new ListTokenStream(this.tokens, this.offset + count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean lookaheadMatches(final TokenType[][] expected) {
        return this.canLookahead(expected.length) && this.allLookaheadsMatch(expected);
    }

    /**
     * Checks if all lookahead tokens match the expected types.
     *
     * @param expected an array of arrays of expected token types for each lookahead position
     * @return {@code true} if all lookahead tokens match, otherwise {@code false}
     */
    private boolean allLookaheadsMatch(final TokenType[][] expected) {
        return IntStream.range(0, expected.length).allMatch(i -> this.lookahead(i).hasAnyType(expected[i]));
    }
}
