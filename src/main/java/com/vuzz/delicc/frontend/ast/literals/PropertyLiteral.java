package com.vuzz.delicc.frontend.ast.literals;

import com.vuzz.delicc.frontend.ast.expressions.Expr;
import com.vuzz.delicc.frontend.lexer.Token;

public class PropertyLiteral extends Expr {
    Token key;
    Expr value;
    public PropertyLiteral(Token key, Expr value) {
        this.key = key;
        this.value = value;
    }
}
