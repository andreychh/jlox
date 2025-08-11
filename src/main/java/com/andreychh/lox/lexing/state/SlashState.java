package com.andreychh.lox.lexing.state;

import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;
import com.andreychh.lox.source.PatternSource;
import com.andreychh.lox.source.Source;
import com.andreychh.lox.token.TokenFromLexeme;

/**
 * Represents a lexing state for handling slash characters and line comments.
 * <p>
 * This state is entered when a '/' character is encountered in the source code.
 * <p>
 * {@snippet :
 * // Creates a slash token for "/"
 * SlashState state = new SlashState(new Source("/"), new LexingResult());
 * LexingStep step = state.next(); // Returns SLASH token
 *
 * // Skips line comment for "//"
 * SlashState commentState = new SlashState(new Source("//comment"), new LexingResult());
 * LexingStep commentStep = commentState.next(); // Skips comment, no token created
 *}
 *
 * @apiNote The state that creates {@code SlashState} must guarantee that {@code source.take(1)} returns {@code "/"}.
 */
public final class SlashState implements LexingState {
    private final Source source;
    private final LexingResult result;

    /**
     * Creates a new slash state with the given source and accumulated result.
     *
     * @param source The source code positioned at a slash character
     * @param result The accumulated lexing result
     */
    public SlashState(final Source source, final LexingResult result) {
        this.source = source;
        this.result = result;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Processes the slash character by examining the following character. If followed by another slash, skips the line
     * comment until a newline character. Otherwise, creates a division operator token and transitions back to the
     * initial state.
     */
    @Override
    public LexingStep next() {
        return new LexingStep(this.state(), false);
    }

    /**
     * Determines the next state after processing the slash character.
     *
     * @return The next lexing state
     */
    private LexingState state() {
        return this.isComment() ? new InitialState(
                new PatternSource(this.source.skip(2)).take("[^\\n]").remaining(),
                this.result
        ) : new InitialState(
                this.source.skip(1),
                this.result.withToken(new TokenFromLexeme("/", this.source.position()))
        );
    }

    /**
     * Checks if the slash is the start of a line comment (i.e., "//")
     *
     * @return {@code true} if the next character is also a slash, {@code false} otherwise
     */
    private boolean isComment() {
        return this.source.hasNext(2) && this.source.peek(1).equals("/");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LexingResult collectResult() {
        return this.result;
    }
}
