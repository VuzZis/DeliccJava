package com.vuzz.delicc.frontend.ast.literals;

import com.vuzz.delicc.frontend.ast.expressions.Expr;

public class StringLiteral extends Expr {
    public String value = "";
    public StringLiteral(String val) {
        value = val;
    }

    @Override
    public String toString() {
        return "\""+String.valueOf(value)+"\"";
    }
}
