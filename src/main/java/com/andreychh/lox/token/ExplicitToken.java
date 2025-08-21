package com.andreychh.lox.token;

import java.util.Arrays;
import java.util.Objects;

import com.andreychh.lox.Position;

/**
 * Represents a token with explicitly defined type, lexeme, and position.
 * <p>
 * It encapsulates the token type, its textual representation (lexeme), and its position in the source code.
 * <p>
 * Example usage:
 * {@snippet :
 * ExplicitToken token = new ExplicitToken(TokenType.IDENTIFIER, "foo", new Position(1, 5));
 *}
 *
 * @see Token
 */
public final class ExplicitToken implements Token {
    private final TokenType type;
    private final String lexeme;
    private final Position position;

    /**
     * Constructs a new {@code ExplicitToken} with the specified type, lexeme, and position.
     *
     * @param type     the type of the token
     * @param lexeme   the textual representation of the token in the source code
     * @param position the position of the token in the source code
     */
    public ExplicitToken(final TokenType type, final String lexeme, final Position position) {
        this.type = type;
        this.lexeme = lexeme;
        this.position = position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String lexeme() {
        return this.lexeme;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasAnyType(final TokenType[] expected) {
        return Arrays.asList(expected).contains(this.type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ExplicitToken{type=%s, lexeme='%s', position=%s}".formatted(this.type, this.lexeme, this.position);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ExplicitToken that = (ExplicitToken) o;
        return this.type == that.type
            && Objects.equals(this.lexeme, that.lexeme)
            && Objects.equals(this.position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.lexeme, this.position);
    }
}
