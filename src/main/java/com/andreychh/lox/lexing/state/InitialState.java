package com.andreychh.lox.lexing.state;

import com.andreychh.lox.error.Error;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.source.Fragment;
import com.andreychh.lox.source.Source;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenType;

/**
 * Represents the entry state that examines characters and dispatches to specialized states or handles simple tokens
 * directly.
 */
public final class InitialState implements LexingState {
    private final Source source;
    private final LexingResult result;

    /**
     * Creates a new initial state.
     *
     * @param source The source code at the current position
     * @param result The accumulated lexing result
     */
    public InitialState(final Source source, final LexingResult result) {
        this.source = source;
        this.result = result;
    }

    /**
     * Examines the next character and transitions to the appropriate state.
     */
    @Override
    public LexingState next() {
        return this.source.hasNext(1) ? this.dispatch(this.source.take(1)) : new EOFState(this.source, this.result);
    }

    /**
     * Dispatches to the next state based on the character.
     *
     * @param taken The fragment representing the next character
     * @return The next lexing state
     */
    private LexingState dispatch(final Fragment taken) {
        return switch (taken.value()) {
            case " ", "\t", "\n", "\r" -> new InitialState(taken.remaining(), this.result);
            case "(", ")", "{", "}", ".", ",", ";", "+", "-", "*" -> {
                Token token = new ExplicitToken(this.tokenType(taken.value()), taken.value(), this.source.position());
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
     * Determines the token type for the given lexeme.
     *
     * @param lexeme The single character lexeme
     * @return The corresponding TokenType
     */
    private TokenType tokenType(final String lexeme) {
        return switch (lexeme) {
            case "(" -> TokenType.LEFT_PAREN;
            case ")" -> TokenType.RIGHT_PAREN;
            case "{" -> TokenType.LEFT_BRACE;
            case "}" -> TokenType.RIGHT_BRACE;
            case "." -> TokenType.DOT;
            case "," -> TokenType.COMMA;
            case ";" -> TokenType.SEMICOLON;
            case "+" -> TokenType.PLUS;
            case "-" -> TokenType.MINUS;
            case "*" -> TokenType.STAR;
            default -> throw new IllegalArgumentException(
                "Cannot determine token type for unexpected lexeme: '%s'".formatted(lexeme)
            );
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFinal() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LexingResult collectResult() {
        return this.result;
    }
}
