package com.vuzz.delicc.frontend.ast.literals;

import com.vuzz.delicc.frontend.ast.expressions.Expr;

public class NullLiteral extends Expr {
    public Object val;

    @Override
    public String toString() {
        return "null";
    }
}
