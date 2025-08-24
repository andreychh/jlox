package com.andreychh.lox.lexing;

import java.util.ArrayList;
import java.util.List;

import com.andreychh.lox.Error;
import com.andreychh.lox.token.Token;

/**
 * Represents the accumulated result of lexical analysis, containing all tokens and errors discovered.
 * <p>
 * This immutable data structure serves as the container for the output of the lexical analysis process. It maintains
 * two collections: successfully parsed tokens and any errors encountered during parsing. The class supports
 * functional-style updates through {@link #withToken(Token)} and {@link #withError(Error)} methods, which return new
 * instances rather than modifying the existing one.
 * <p>
 * The lexing process can continue even after encountering errors, allowing the collection of multiple issues in a
 * single pass. This enables better error reporting and recovery strategies in later compilation phases.
 */
public final class LexingResult {
    private final List<Token> tokens;
    private final List<Error> errors;

    /**
     * Creates a new lexing result with the specified tokens and errors.
     * <p>
     * This constructor creates defensive copies of the provided lists to ensure immutability.
     *
     * @param tokens The list of successfully parsed tokens
     * @param errors The list of errors encountered during lexical analysis
     */
    public LexingResult(final List<Token> tokens, final List<Error> errors) {
        this.tokens = new ArrayList<>(tokens);
        this.errors = new ArrayList<>(errors);
    }

    /**
     * Creates a new empty lexing result with no tokens and no errors.
     * <p>
     * This constructor is typically used to initialize the lexical analysis process or in test scenarios where a clean
     * slate is needed.
     */
    public LexingResult() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Creates a new lexing result by adding a token to the current result.
     * <p>
     * This method follows the immutable pattern - it does not modify the current instance but returns a new
     * {@code LexingResult} containing all previous tokens plus the new one. The token is appended to the end of the
     * token list, preserving the order of discovery.
     *
     * @param token The token to add to the result
     * @return A new {@code LexingResult} instance containing the additional token
     */
    public LexingResult withToken(final Token token) {
        List<Token> newTokens = new ArrayList<>(this.tokens);
        newTokens.add(token);
        return new LexingResult(newTokens, this.errors);
    }

    /**
     * Creates a new lexing result by adding an error to the current result.
     * <p>
     * This method follows the immutable pattern - it does not modify the current instance but returns a new
     * {@code LexingResult} containing all previous errors plus the new one. The error is appended to the end of the
     * error list, preserving the order of discovery.
     * <p>
     * Adding errors does not prevent further token collection, allowing the lexical analysis to continue and
     * potentially discover additional issues.
     *
     * @param error The error to add to the result
     * @return A new {@code LexingResult} instance containing the additional error
     */
    public LexingResult withError(final Error error) {
        List<Error> newErrors = new ArrayList<>(this.errors);
        newErrors.add(error);
        return new LexingResult(this.tokens, newErrors);
    }

    /**
     * Returns the list of tokens discovered during lexical analysis.
     * <p>
     * The returned list contains tokens in the order they were discovered in the source code. This list is immutable -
     * modifications to it will not affect the {@code LexingResult}.
     *
     * @return An unmodifiable view of the tokens collected during analysis
     */
    public List<Token> tokens() {
        return List.copyOf(this.tokens);
    }

    /**
     * Returns the list of errors encountered during lexical analysis.
     * <p>
     * The returned list contains errors in the order they were discovered in the source code. Even when errors are
     * present, the lexical analysis may have continued and collected additional tokens. This list is immutable -
     * modifications to it will not affect the {@code LexingResult}.
     *
     * @return An unmodifiable view of the errors encountered during analysis
     */
    public List<Error> errors() {
        return List.copyOf(this.errors);
    }
}
