package com.vuzz.delicc.frontend.ast.literals;

import com.vuzz.delicc.frontend.ast.expressions.Expr;

public class BooleanLiteral extends Expr {
    public boolean value = false;
    public BooleanLiteral(boolean val) {
        value = val;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
