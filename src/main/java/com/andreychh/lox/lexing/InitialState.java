package com.andreychh.lox.lexing;

import com.andreychh.lox.Source;
import com.andreychh.lox.SimpleToken;
import com.andreychh.lox.TokenType;
import com.andreychh.lox.Tokens;

public final class InitialState implements LexingState {
    private final Tokens tokens;
    private final Source source;

    public InitialState(final Tokens tokens, final Source source) {
        this.tokens = tokens;
        this.source = source;
    }

    @Override
    public LexingState process() {
        if (!this.source.canPeek(0)) {
            return new FinalState(this.tokens, this.source);
        }
        char character = this.source.peek(0);
        return switch (character) {
            case ' ', '\t', '\n', '\r' -> new InitialState(this.tokens, this.source.skip(1));
            case '(' -> new InitialState(
                    this.tokens.withToken(new SimpleToken(TokenType.LEFT_PAREN, "(", this.source.position())),
                    this.source.skip(1)
            );
            case ')' -> new InitialState(
                    this.tokens.withToken(new SimpleToken(TokenType.RIGHT_PAREN, ")", this.source.position())),
                    this.source.skip(1)
            );
            case '{' -> new InitialState(
                    this.tokens.withToken(new SimpleToken(TokenType.LEFT_BRACE, "{", this.source.position())),
                    this.source.skip(1)
            );
            case '}' -> new InitialState(
                    this.tokens.withToken(new SimpleToken(TokenType.RIGHT_BRACE, "}", this.source.position())),
                    this.source.skip(1)
            );
            case '.' -> new InitialState(
                    this.tokens.withToken(new SimpleToken(TokenType.DOT, ".", this.source.position())),
                    this.source.skip(1)
            );
            case ',' -> new InitialState(
                    this.tokens.withToken(new SimpleToken(TokenType.COMMA, ",", this.source.position())),
                    this.source.skip(1)
            );
            case ';' -> new InitialState(
                    this.tokens.withToken(new SimpleToken(TokenType.SEMICOLON, ";", this.source.position())),
                    this.source.skip(1)
            );
            case '+' -> new InitialState(
                    this.tokens.withToken(new SimpleToken(TokenType.PLUS, "+", this.source.position())),
                    this.source.skip(1)
            );
            case '-' -> new InitialState(
                    this.tokens.withToken(new SimpleToken(TokenType.MINUS, "-", this.source.position())),
                    this.source.skip(1)
            );
            case '*' -> new InitialState(
                    this.tokens.withToken(new SimpleToken(TokenType.STAR, "*", this.source.position())),
                    this.source.skip(1)
            );
            case '!' -> new BangState(this.tokens, this.source);
            case '=' -> new EqualState(this.tokens, this.source);
            case '>' -> new GreaterState(this.tokens, this.source);
            case '<' -> new LessState(this.tokens, this.source);
            case '/' -> new SlashState(this.tokens, this.source);
            case '"' -> new StringState(this.tokens, this.source);
            default -> throw new IllegalStateException(
                    "Unexpected character '" + character + "' at position " + this.source.position()
            );
        };
    }

    @Override
    public boolean isTerminal() {
        return false;
    }

    @Override
    public Tokens tokens() {
        return this.tokens;
    }
}
