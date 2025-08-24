package com.andreychh.lox.lexing.state;

import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.source.Fragment;
import com.andreychh.lox.source.PatternSource;
import com.andreychh.lox.source.Source;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenType;

/**
 * Represents a state that processes identifiers and keywords by consuming alphanumeric
 * characters and underscores.
 *
 * @implNote Expects {@code source.take(1)} to return a letter or underscore
 */
public final class IdentifierState implements LexingState {
    private final Source source;
    private final LexingResult result;

    /**
     * Creates a new identifier state.
     *
     * @param source The source code positioned at an identifier start character
     * @param result The accumulated lexing result
     */
    public IdentifierState(final Source source, final LexingResult result) {
        this.source = source;
        this.result = result;
    }

    /**
     * Consumes the identifier and creates the appropriate token (keyword or identifier).
     */
    @Override
    public LexingState next() {
        Fragment taken = new PatternSource(this.source).take("[0-9a-zA-Z_]");
        Token token = new ExplicitToken(this.tokenType(taken.value()), taken.value(), this.source.position());
        return new InitialState(taken.remaining(), this.result.withToken(token));
    }

    /**
     * Determines the token type for the given lexeme.
     *
     * @param lexeme The identifier lexeme to classify
     * @return The appropriate TokenType (keyword or IDENTIFIER)
     */
    private TokenType tokenType(final String lexeme) {
        return switch (lexeme) {
            case "and" -> TokenType.AND;
            case "class" -> TokenType.CLASS;
            case "else" -> TokenType.ELSE;
            case "false" -> TokenType.FALSE;
            case "for" -> TokenType.FOR;
            case "fun" -> TokenType.FUN;
            case "if" -> TokenType.IF;
            case "nil" -> TokenType.NIL;
            case "or" -> TokenType.OR;
            case "print" -> TokenType.PRINT;
            case "return" -> TokenType.RETURN;
            case "super" -> TokenType.SUPER;
            case "this" -> TokenType.THIS;
            case "true" -> TokenType.TRUE;
            case "var" -> TokenType.VAR;
            case "while" -> TokenType.WHILE;
            default -> TokenType.IDENTIFIER;
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
