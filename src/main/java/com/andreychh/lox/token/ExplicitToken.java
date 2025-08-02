package com.andreychh.lox.token;

import com.andreychh.lox.Position;

public final class ExplicitToken implements Token {
    private final TokenType type;
    private final String lexeme;
    private final Position position;

    public ExplicitToken(final TokenType type, final String lexeme, final Position position) {
        this.type = type;
        this.lexeme = lexeme;
        this.position = position;
    }

    @Override
    public String format() {
        return "Token(%s, \"%s\", %s)".formatted(this.type, this.lexeme, this.position.format());
    }
}
