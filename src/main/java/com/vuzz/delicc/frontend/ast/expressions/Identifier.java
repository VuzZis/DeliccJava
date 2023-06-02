package com.vuzz.delicc.frontend.ast.expressions;

import com.vuzz.delicc.frontend.ast.expressions.Expr;

public class Identifier extends Expr {
    public String symbol = "";
    public Identifier(String tokenVal) {
        symbol = tokenVal;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
