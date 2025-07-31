package com.andreychh.lox;

import java.util.ArrayList;
import java.util.List;

public final class Tokens {
    private final Source source;

    public Tokens(final Source source) {
        this.source = source;
    }

    public List<Token> tokens() {
        List<Token> tokens = new ArrayList<>();
        while (this.source.canSkip(1)) {
            tokens.add(this.scanToken());
        }
        tokens.add(new Token(TokenType.EOF, "\0", source.position()));
        return tokens;
    }

    /**
     * todo: refactor scanToken
     */
    private Token scanToken() {
        if (!this.source.canPeek(0)) {
            return new Token(TokenType.EOF, "\0", source.position());
        }
        char c = this.source.peek(0);
        switch (c) {
            case '(' -> {
                Token token = new Token(TokenType.LEFT_PAREN, "(", source.position());
                this.source.skip(1);
                return token;
            }
            case ')' -> {
                Token token = new Token(TokenType.RIGHT_PAREN, ")", source.position());
                this.source.skip(1);
                return token;
            }
            case '{' -> {
                Token token = new Token(TokenType.LEFT_BRACE, "{", source.position());
                this.source.skip(1);
                return token;
            }
            case '}' -> {
                Token token = new Token(TokenType.RIGHT_BRACE, "}", source.position());
                this.source.skip(1);
                return token;
            }
            case ',' -> {
                Token token = new Token(TokenType.COMMA, ",", source.position());
                this.source.skip(1);
                return token;
            }
            case '.' -> {
                Token token = new Token(TokenType.DOT, ".", source.position());
                this.source.skip(1);
                return token;
            }
            case '-' -> {
                Token token = new Token(TokenType.MINUS, "-", source.position());
                this.source.skip(1);
                return token;
            }
            case '+' -> {
                Token token = new Token(TokenType.PLUS, "+", source.position());
                this.source.skip(1);
                return token;
            }
            case ';' -> {
                Token token = new Token(TokenType.SEMICOLON, ";", source.position());
                this.source.skip(1);
                return token;
            }
            case '*' -> {
                Token token = new Token(TokenType.STAR, "*", source.position());
                this.source.skip(1);
                return token;
            }
            case '!' -> {
                if (this.source.peek(1) == '=') {
                    Token token = new Token(TokenType.BANG_EQUAL, "!=", source.position());
                    this.source.skip(2);
                    return token;
                } else {
                    Token token = new Token(TokenType.BANG, "!", source.position());
                    this.source.skip(1);
                    return token;
                }
            }
            case '=' -> {
                if (this.source.peek(1) == '=') {
                    Token token = new Token(TokenType.EQUAL_EQUAL, "==", source.position());
                    this.source.skip(2);
                    return token;
                } else {
                    Token token = new Token(TokenType.EQUAL, "=", source.position());
                    this.source.skip(1);
                    return token;
                }
            }
            case '<' -> {
                if (this.source.peek(1) == '=') {
                    Token token = new Token(TokenType.LESS_EQUAL, "<=", source.position());
                    this.source.skip(2);
                    return token;
                } else {
                    Token token = new Token(TokenType.LESS, "<", source.position());
                    this.source.skip(1);
                    return token;
                }
            }
            case '>' -> {
                if (this.source.peek(1) == '=') {
                    Token token = new Token(TokenType.GREATER_EQUAL, ">=", source.position());
                    this.source.skip(2);
                    return token;
                } else {
                    Token token = new Token(TokenType.GREATER, ">", source.position());
                    this.source.skip(1);
                    return token;
                }
            }
            case '/' -> {
                if (this.source.peek(1) == '/') {
                    while (this.source.canPeek(1) && this.source.peek(1) != '\n') {
                        this.source.skip(1);
                    }
                    return this.scanToken();
                } else {
                    Token token = new Token(TokenType.SLASH, "/", source.position());
                    this.source.skip(1);
                    return token;
                }
            }
            case ' ', '\r', '\t', '\n' -> {
                this.source.skip(1);
                return this.scanToken();
            }
            case '"' -> {
                String lexeme = "";
                do {
                    lexeme = lexeme + this.source.peek(0);
                    if (!this.source.canSkip(1)) {
                        throw new IllegalStateException("Unexpected value: " + c);
                    }
                    this.source.skip(1);
                } while (this.source.canPeek(0) && this.source.peek(0) != '"');
                lexeme = lexeme + this.source.peek(0);
                this.source.skip(1);
                return new Token(TokenType.STRING, lexeme, source.position());
            }
            default -> throw new IllegalStateException("Unexpected value: " + c);
        }
    }
}
