package com.vuzz.delicc.frontend.lexer;

public class Token {
    public TokenPath id;
    public String symbol;
    public Token(TokenPath path, String symbol) {
        this.id = path;
        this.symbol = symbol;
    }
}
