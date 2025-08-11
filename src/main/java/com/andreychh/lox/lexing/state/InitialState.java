package com.andreychh.lox.lexing.state;

import com.andreychh.lox.Error;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;
import com.andreychh.lox.source.Fragment;
import com.andreychh.lox.source.Source;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenFromLexeme;

/**
 * Represents the initial state of the lexical analysis process.
 * <p>
 * This state is responsible for examining the next character in the source and transitioning to the appropriate
 * specialized state based on the character. It directly handles simple single-character tokens and whitespace.
 */
public final class InitialState implements LexingState {
    private final Source source;
    private final LexingResult result;

    /**
     * Creates a new initial state with the given source and accumulated result.
     *
     * @param source The source code at the current position
     * @param result The accumulated lexing result
     */
    public InitialState(final Source source, final LexingResult result) {
        this.source = source;
        this.result = result;
    }

    /**
     * Creates a new initial state with the given source and an empty result.
     *
     * @param source The source code at the starting position
     */
    public InitialState(final Source source) {
        this(source, new LexingResult());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Examines the next character and transitions to the appropriate state or directly handles simple tokens.
     */
    @Override
    public LexingStep next() {
        return new LexingStep(this.state(), false);
    }

    /**
     * Determines the next state based on the current source.
     *
     * @return The next lexing state
     */
    private LexingState state() {
        return this.source.hasNext(1) ? this.nextState(this.source.take(1)) : new EOFState(this.source, this.result);
    }

    /**
     * Determines the next state based on the taken fragment.
     *
     * @param taken The fragment representing the next character
     * @return The next lexing state
     */
    private LexingState nextState(final Fragment taken) {
        return switch (taken.value()) {
            case " ", "\t", "\n", "\r" -> new InitialState(taken.remaining(), this.result);
            case "(", ")", "{", "}", ".", ",", ";", "+", "-", "*" -> {
                Token token = new TokenFromLexeme(taken.value(), this.source.position());
                yield new InitialState(taken.remaining(), this.result.withToken(token));
            }
            case "!", "=", ">", "<" -> new CompoundOperatorState(this.source, this.result);
            case "/" -> new SlashState(this.source, this.result);
            case "\"" -> new StringState(this.source, this.result);
            case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> new NumberState(this.source, this.result);
            case "_", "a", "b", "c", "d", "e", "f", "g", "h", "i",
                 "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                 "t", "u", "v", "w", "x", "y", "z", "A", "B", "C",
                 "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                 "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
                 "X", "Y", "Z" -> new IdentifierState(this.source, this.result);
            default -> {
                String message = "Unexpected character '%s'.".formatted(taken.value());
                Error error = new Error(message, this.source.position());
                yield new InitialState(taken.remaining(), this.result.withError(error));
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LexingResult collectResult() {
        return this.result;
    }
}
