package com.vuzz.delicc.frontend.lexer;

public class DoubleToken {

    public String sym1 = "";
    public String sym2 = "";

    public TokenPath path;

    public DoubleToken(TokenPath path, String symbol, String symbol2) {
        this.path = path;
        this.sym1 = symbol;
        this.sym2 = symbol2;
    }

    public Token toToken() {
        return new Token(path,sym1+sym2);
    }

}
