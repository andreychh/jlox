package com.andreychh.lox.lexing.state;

import com.andreychh.lox.Source;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenType;

/**
 * A lexing state for processing identifiers and keywords.
 * <p>
 * {@snippet :
 * // Creates an identifier token for "variable"
 * IdentifierState state = new IdentifierState(new Source("variable"), new LexingResult());
 * LexingStep step = state.next(); // Returns IDENTIFIER token
 *
 * // Creates a keyword token for "while"
 * IdentifierState keywordState = new IdentifierState(new Source("while"), new LexingResult());
 * LexingStep keywordStep = keywordState.next(); // Returns WHILE token
 *
 * // Creates an identifier token for "_private"
 * IdentifierState underscoreState = new IdentifierState(new Source("_private"), new LexingResult());
 * LexingStep underscoreStep = underscoreState.next(); // Returns IDENTIFIER token
 *}
 *
 * @implNote This state should only be created when the source is positioned at a letter or underscore character.
 */
public final class IdentifierState implements LexingState {
    private final Source source;
    private final LexingResult result;

    /**
     * Creates a new identifier state with the given source and accumulated result.
     *
     * @param source The source code positioned at an identifier start character
     * @param result The accumulated lexing result
     */
    public IdentifierState(final Source source, final LexingResult result) {
        this.source = source;
        this.result = result;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Processes the identifier by consuming consecutive alphanumeric characters and underscores. Determines whether the
     * resulting lexeme is a reserved keyword or a regular identifier, then creates the appropriate token type.
     */
    @Override
    public LexingStep next() {
        return new LexingStep(
                new InitialState(
                        this.source.skipWhile(this::isAlphaNumeric),
                        this.result.withToken(this.tokenFromLexeme(this.source.takeWhile(this::isAlphaNumeric)))
                ),
                false
        );
    }

    /**
     * Creates a token from the given lexeme, determining whether it represents a keyword or identifier.
     *
     * @param lexeme The identifier lexeme to classify
     * @return A token with the appropriate type (keyword or identifier)
     */
    private Token tokenFromLexeme(final String lexeme) {
        TokenType type = switch (lexeme) {
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
        return new ExplicitToken(type, lexeme, this.source.position());
    }

    /**
     * Determines whether a character is valid within an identifier.
     *
     * @param c The character to check
     * @return True if the character is alphanumeric or underscore
     */
    private boolean isAlphaNumeric(final char c) {
        return c == '_'
                || (c >= 'a' && c <= 'z')
                || (c >= 'A' && c <= 'Z')
                || (c >= '0' && c <= '9');
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LexingResult collectResult() {
        return this.result;
    }
}
