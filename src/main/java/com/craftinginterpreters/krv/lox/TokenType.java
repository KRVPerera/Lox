package com.craftinginterpreters.krv.lox;

enum TokenType {
    // Single-character tokens.
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
    COMA, DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR,

    // One or two character tokens
    BANG, BANG_EQUAL,
    EQUAL, EQUAL_EQUAL,
    GREATER, GREATER_EQUAL,

    // Literals
    IDENTIFIER, STRING, NUMBER,

    // Keywordds
    AND, CLASS, ELSE, FALSE, FUN, FOR, IF, NIL, OR,
    PRINT, RETURN, SUPER, THIS, TRUE, VAR, WHILE,

    EOF
}
