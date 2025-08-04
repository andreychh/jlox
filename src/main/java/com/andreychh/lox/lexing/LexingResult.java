package com.andreychh.lox.lexing;

import java.util.ArrayList;
import java.util.List;

import com.andreychh.lox.Error;
import com.andreychh.lox.token.Token;

/**
 * Represents the accumulated result of the lexical analysis process.
 * <p>
 * This class acts as an immutable container for all tokens and errors found
 * during lexical analysis. New instances are created whenever tokens or errors
 * are added, following the immutability principle.
 */
public final class LexingResult {
    private final List<Token> tokens;
    private final List<Error> errors;

    /**
     * Creates a new lexing result with the specified tokens and errors.
     *
     * @param tokens The list of tokens
     * @param errors The list of errors
     */
    public LexingResult(final List<Token> tokens, final List<Error> errors) {
        this.tokens = tokens;
        this.errors = errors;
    }

    /**
     * Creates a new empty lexing result with no tokens and no errors.
     */
    public LexingResult() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Creates a new lexing result by adding a token to this result.
     *
     * @param token The token to add
     * @return A new LexingResult with the added token
     */
    public LexingResult withToken(final Token token) {
        List<Token> tokens = new ArrayList<>(this.tokens);
        tokens.add(token);
        return new LexingResult(tokens, this.errors);
    }

    /**
     * Creates a new lexing result by adding an error to this result.
     *
     * @param error The error to add
     * @return A new LexingResult with the added error
     */
    public LexingResult withError(final Error error) {
        List<Error> errors = new ArrayList<>(this.errors);
        errors.add(error);
        return new LexingResult(this.tokens, errors);
    }

    /**
     * Returns the list of tokens found during lexical analysis.
     *
     * @return An iterable collection of tokens
     */
    public Iterable<Token> tokens() {
        return this.tokens;
    }

    /**
     * Returns the list of errors encountered during lexical analysis.
     *
     * @return An iterable collection of errors
     */
    public Iterable<Error> errors() {
        return this.errors;
    }
}
