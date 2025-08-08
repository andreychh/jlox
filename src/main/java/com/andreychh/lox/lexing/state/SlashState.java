package com.andreychh.lox.lexing.state;

import com.andreychh.lox.Source;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;
import com.andreychh.lox.token.TokenFromLexeme;

/**
 * A lexing state for handling slash characters and line comments.
 * <p>
 * This state is entered when a '/' character is encountered in the source code.
 * <p>
 * {@snippet :
 * // Creates a division token for "/"
 * SlashState state = new SlashState(new Source("/"), new LexingResult());
 * LexingStep step = state.next(); // Returns division token
 *
 * // Skips line comment for "//"
 * SlashState commentState = new SlashState(new Source("//comment"), new LexingResult());
 * LexingStep commentStep = commentState.next(); // Skips comment, no token created
 *}
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
        if (this.source.canPeek(1) && this.source.peek(1) == '/') {
            return new LexingStep(
                    new InitialState(
                            this.source.skip(2).skipWhile(c -> c != '\n'),
                            this.result
                    ),
                    false
            );
        }
        return new LexingStep(
                new InitialState(
                        this.source.skip(1),
                        this.result.withToken(new TokenFromLexeme("/", this.source.position()))
                ),
                false
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LexingResult collectResult() {
        return this.result;
    }
}
