package com.andreychh.lox.lexing.state;

import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;
import com.andreychh.lox.source.Fragment;
import com.andreychh.lox.source.PatternSource;
import com.andreychh.lox.source.Source;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenType;

/**
 * Represents a lexing state for processing identifiers and keywords.
 * <p>
 * This state is responsible for consuming a sequence of alphanumeric characters and underscores, and for producing
 * either an identifier or a keyword token.
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
 * @implNote The state that creates {@code IdentifierState} must guarantee that {@code source.take(1)} returns a letter
 * or underscore character.
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
        Fragment taken = new PatternSource(this.source).take("[0-9a-zA-Z_]");
        Token token = new ExplicitToken(this.tokenType(taken.value()), taken.value(), this.source.position());
        LexingState state = new InitialState(taken.remaining(), this.result.withToken(token));
        return new LexingStep(state, false);
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
    public LexingResult collectResult() {
        return this.result;
    }
}
