package com.craftinginterpreters.krv.lox;

import java.util.List;

import static com.craftinginterpreters.krv.lox.TokenType.BANG;
import static com.craftinginterpreters.krv.lox.TokenType.BANG_EQUAL;
import static com.craftinginterpreters.krv.lox.TokenType.EOF;
import static com.craftinginterpreters.krv.lox.TokenType.EQUAL_EQUAL;
import static com.craftinginterpreters.krv.lox.TokenType.FALSE;
import static com.craftinginterpreters.krv.lox.TokenType.GREATER;
import static com.craftinginterpreters.krv.lox.TokenType.GREATER_EQUAL;
import static com.craftinginterpreters.krv.lox.TokenType.LEFT_PAREN;
import static com.craftinginterpreters.krv.lox.TokenType.LESS;
import static com.craftinginterpreters.krv.lox.TokenType.LESS_EQUAL;
import static com.craftinginterpreters.krv.lox.TokenType.MINUS;
import static com.craftinginterpreters.krv.lox.TokenType.NIL;
import static com.craftinginterpreters.krv.lox.TokenType.NUMBER;
import static com.craftinginterpreters.krv.lox.TokenType.PLUS;
import static com.craftinginterpreters.krv.lox.TokenType.RIGHT_PAREN;
import static com.craftinginterpreters.krv.lox.TokenType.SLASH;
import static com.craftinginterpreters.krv.lox.TokenType.STAR;
import static com.craftinginterpreters.krv.lox.TokenType.STRING;
import static com.craftinginterpreters.krv.lox.TokenType.TRUE;

public class Parser {

    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();

        throw error(peek(), message);
    }

    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    private Expr expression() {
        return equality();
    }

    private Expr equality() {
        Expr expr = comparison();

        while (match(BANG_EQUAL, EQUAL_EQUAL)) {
            Token operator = previous();
            Expr right = comparison();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr comparison() {
        Expr expr = term();

        while (match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)) {
            Token operator = previous();
            Expr right = term();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr term() {
        Expr expr = factor();

        while (match(MINUS, PLUS)) {
            Token operator = previous();
            Expr right = factor();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr factor() {
        Expr expr = unary();

        while (match(SLASH, STAR)) {
            Token operator = previous();
            Expr right = factor();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr unary() {
        if (match(BANG, MINUS)) {
            Token operator = previous();
            Expr right = unary();
            return new Expr.Unary(operator, right);
        }

        return primary();
    }

    private Expr primary() {
        if (match(FALSE)) return new Expr.Literal(false);
        if (match(TRUE)) return new Expr.Literal(true);
        if (match(NIL)) return new Expr.Literal(null);

        if (match(NUMBER, STRING)) {
            return new Expr.Literal(previous().literal);
        }

        if (match(LEFT_PAREN)) {
            Expr expr = expression();
            consume(RIGHT_PAREN, "Expect ')' after expression");
            return new Expr.Grouping(expr);
        }
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private Exception error(Token token, String message) {
        Lox.error(token, message);
        return new ParseError();
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type == EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private static class ParseError extends RuntimeException {

    }
}
