package com.andreychh.lox.token;

import com.andreychh.lox.Position;

/**
 * A token implementation that infers its type from its lexeme.
 * <p>
 * This class automatically determines the token type based on the lexeme's
 * textual content by matching against known keywords and operators.
 */
public final class TokenFromLexeme implements Token {
    private final String lexeme;
    private final Position position;

    /**
     * Creates a new token from a lexeme.
     *
     * @param lexeme   The token's textual representation in the source code
     * @param position The token's position in the source code
     */
    public TokenFromLexeme(final String lexeme, final Position position) {
        this.lexeme = lexeme;
        this.position = position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String format() {
        return "Token(%s, \"%s\", %s)".formatted(this.type(), this.lexeme, this.position.format());
    }

    /**
     * Determines the token type based on the lexeme.
     *
     * @return The appropriate token type for this lexeme
     * @throws RuntimeException if the lexeme does not match any known token type
     */
    private TokenType type() {
        return switch (this.lexeme) {
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
            case "{" -> TokenType.LEFT_BRACE;
            case "}" -> TokenType.RIGHT_BRACE;
            case "(" -> TokenType.LEFT_PAREN;
            case ")" -> TokenType.RIGHT_PAREN;
            case "." -> TokenType.DOT;
            case "," -> TokenType.COMMA;
            case ";" -> TokenType.SEMICOLON;
            case "+" -> TokenType.PLUS;
            case "-" -> TokenType.MINUS;
            case "*" -> TokenType.STAR;
            case "!" -> TokenType.BANG;
            case "!=" -> TokenType.BANG_EQUAL;
            case "=" -> TokenType.EQUAL;
            case "==" -> TokenType.EQUAL_EQUAL;
            case ">" -> TokenType.GREATER;
            case ">=" -> TokenType.GREATER_EQUAL;
            case "<" -> TokenType.LESS;
            case "<=" -> TokenType.LESS_EQUAL;
            default -> throw new RuntimeException();
        };
    }
}
