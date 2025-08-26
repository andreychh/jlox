package com.andreychh.lox.parsing;

import java.util.concurrent.atomic.AtomicReference;

import com.andreychh.lox.parsing.rule.BinaryOperatorRule;
import com.andreychh.lox.parsing.rule.ParsingRule;
import com.andreychh.lox.parsing.rule.PrimaryExpressionRule;
import com.andreychh.lox.parsing.rule.UnaryOperatorRule;
import com.andreychh.lox.token.TokenType;
import com.andreychh.lox.token.stream.ListTokenStream;

/**
 * Recursive descent parser for Lox expressions.
 * <p>
 * Implements operator precedence grammar:
 * {@snippet lang = ebnf:
 * expression  ::= equality ;
 * equality    ::= comparison ( ( "!=" | "==" ) comparison )* ;
 * comparison  ::= term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
 * term        ::= factor ( ( "-" | "+" ) factor )* ;
 * factor      ::= unary ( ( "/" | "*" ) unary )* ;
 * unary       ::= ( "!" | "-" ) unary | primary ;
 * primary     ::= NUMBER | STRING | "true" | "false" | "nil" | "(" expression ")" ;
 *}
 * <p>
 * Usage:
 * {@snippet :
 * Parser parser = new Parser(tokens);
 * ParsingReport report = parser.parse();
 *}
 */
public final class Parser {
    private final ListTokenStream tokens;

    /**
     * Creates parser with token stream.
     *
     * @param tokens stream to parse
     */
    public Parser(final ListTokenStream tokens) {
        this.tokens = tokens;
    }

    /**
     * Parses tokens into AST.
     *
     * @return parsing report with expression and errors
     */
    public ParsingReport parse() {
        AtomicReference<ParsingRule> equalityRef = new AtomicReference<>();
        AtomicReference<ParsingRule> comparisonRef = new AtomicReference<>();
        AtomicReference<ParsingRule> termRef = new AtomicReference<>();
        AtomicReference<ParsingRule> factorRef = new AtomicReference<>();
        AtomicReference<ParsingRule> unaryRef = new AtomicReference<>();
        AtomicReference<ParsingRule> primaryRef = new AtomicReference<>();

        equalityRef.set(new BinaryOperatorRule(
            comparisonRef::get,
            new TokenType[][]{{TokenType.EQUAL_EQUAL, TokenType.BANG_EQUAL}}
        ));

        comparisonRef.set(new BinaryOperatorRule(
            termRef::get,
            new TokenType[][]{{TokenType.GREATER, TokenType.GREATER_EQUAL, TokenType.LESS, TokenType.LESS_EQUAL}}
        ));

        termRef.set(new BinaryOperatorRule(
            factorRef::get,
            new TokenType[][]{{TokenType.MINUS, TokenType.PLUS}}
        ));

        factorRef.set(new BinaryOperatorRule(
            unaryRef::get,
            new TokenType[][]{{TokenType.SLASH, TokenType.STAR}}
        ));

        unaryRef.set(new UnaryOperatorRule(
            primaryRef::get,
            new TokenType[][]{{TokenType.BANG, TokenType.MINUS}}
        ));

        primaryRef.set(new PrimaryExpressionRule(
            equalityRef::get
        ));

        return equalityRef.get().parse(this.tokens).report();
    }
}
