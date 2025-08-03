package com.andreychh.lox.token;

import com.andreychh.lox.Position;

/**
 * A token implementation that explicitly specifies its type.
 * <p>
 * This is used for tokens whose type cannot be directly inferred from
 * their lexeme, such as EOF tokens.
 */
public final class ExplicitToken implements Token {
    private final TokenType type;
    private final String lexeme;
    private final Position position;

    /**
     * Creates a new explicit token.
     *
     * @param type     The token type
     * @param lexeme   The token's textual representation in the source code
     * @param position The token's position in the source code
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
    public String format() {
        return "Token(%s, \"%s\", %s)".formatted(this.type, this.lexeme, this.position.format());
    }
}
