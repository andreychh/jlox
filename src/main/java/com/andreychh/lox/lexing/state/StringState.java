package com.andreychh.lox.lexing.state;

import com.andreychh.lox.Error;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;
import com.andreychh.lox.source.Fragment;
import com.andreychh.lox.source.PatternSource;
import com.andreychh.lox.source.Source;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenType;

/**
 * Represents a lexing state for processing string literals.
 * <p>
 * This state is entered when a '"' character is encountered in the source code.
 * <p>
 * {@snippet :
 * // Creates a string token for "hello"
 * StringState state = new StringState(new Source("\"hello\""), new LexingResult());
 * LexingStep step = state.next(); // Returns STRING token
 *
 * // Handles unterminated string
 * StringState unterminatedState = new StringState(new Source("\"hello"), new LexingResult());
 * LexingStep errorStep = unterminatedState.next(); // Returns error for unterminated string
 *}
 *
 * @apiNote The state that creates {@code StringState} must guarantee that {@code source.take(1)} returns {@code "\""}.
 */
public final class StringState implements LexingState {
    private final Source source;
    private final LexingResult result;

    /**
     * Creates a new string state with the given source and accumulated result.
     *
     * @param source The source code positioned at a string literal
     * @param result The accumulated lexing result
     */
    public StringState(final Source source, final LexingResult result) {
        this.source = source;
        this.result = result;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Processes the string literal by consuming characters until the closing quote is found. If the string is not
     * properly terminated, generates an error. The resulting token includes the quote characters in the lexeme.
     */
    @Override
    public LexingStep next() {
        Fragment taken = new PatternSource(this.source.skip(1)).take("[^\"]");
        if (!taken.remaining().hasNext(1)) {
            Error error = new Error("Unterminated string literal", this.source.position());
            LexingState state = new EOFState(taken.remaining(), this.result.withError(error));
            return new LexingStep(state, false);
        }
        Token token = new ExplicitToken(TokenType.STRING, "\"%s\"".formatted(taken.value()), this.source.position());
        LexingState state = new InitialState(taken.remaining().skip(1), this.result.withToken(token));
        return new LexingStep(state, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LexingResult collectResult() {
        return this.result;
    }
}
