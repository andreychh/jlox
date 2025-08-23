package com.andreychh.lox.lexing.state;

import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.source.Fragment;
import com.andreychh.lox.source.Source;
import com.andreychh.lox.token.ExplicitToken;
import com.andreychh.lox.token.Token;
import com.andreychh.lox.token.TokenType;

/**
 * Represents a state that handles ambiguous operators like '=', '!', '>', '<' which can be single or compound ('==',
 * '!=', '>=', '<=') depending on the following character.
 *
 * @apiNote Expects {@code source.take(1)} to return one of: "!", "=", ">", "<"
 */
public final class CompoundOperatorState implements LexingState {
    private final Source source;
    private final LexingResult result;

    /**
     * Creates a new compound operator state.
     *
     * @param source The source code at the current position
     * @param result The accumulated lexing result
     */
    public CompoundOperatorState(final Source source, final LexingResult result) {
        this.source = source;
        this.result = result;
    }

    /**
     * Looks ahead to determine if this is a compound operator and creates the appropriate token.
     */
    @Override
    public LexingState next() {
        Fragment taken = this.takeOperator();
        Token token = new ExplicitToken(this.tokenType(taken.value()), taken.value(), this.source.position());
        return new InitialState(taken.remaining(), this.result.withToken(token));
    }

    /**
     * Takes the operator fragment from the source, handling both single and compound forms.
     *
     * @return The fragment representing the operator lexeme and the remaining source
     */
    private Fragment takeOperator() {
        return this.isCompound() ? this.source.take(2) : this.source.take(1);
    }

    /**
     * Checks if the current operator is compound by looking for '=' as the next character.
     *
     * @return {@code true} if the next character is '=', {@code false} otherwise
     */
    private boolean isCompound() {
        return this.source.hasNext(2) && this.source.peek(1).equals("=");
    }

    /**
     * Determines the token type for the given lexeme.
     *
     * @param lexeme The operator lexeme to classify
     * @return The appropriate TokenType
     */
    private TokenType tokenType(final String lexeme) {
        return switch (lexeme) {
            case "=" -> TokenType.EQUAL;
            case "==" -> TokenType.EQUAL_EQUAL;
            case "!" -> TokenType.BANG;
            case "!=" -> TokenType.BANG_EQUAL;
            case ">" -> TokenType.GREATER;
            case ">=" -> TokenType.GREATER_EQUAL;
            case "<" -> TokenType.LESS;
            case "<=" -> TokenType.LESS_EQUAL;
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
