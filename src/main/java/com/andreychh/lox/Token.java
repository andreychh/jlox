package com.andreychh.lox;

public final class Token {
    private final TokenType type;
    private final String lexeme;
    private final Position position;

    public Token(final TokenType type, final String lexeme, final Position position) {
        this.type = type;
        this.lexeme = lexeme;
        this.position = position;
    }

    public String format() {
        return "Token(%s, \"%s\", %s)".formatted(this.type, this.lexeme, this.position.format());
    }
}
