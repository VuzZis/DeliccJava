package com.vuzz.delicc.frontend.ast.literals;

import com.vuzz.delicc.frontend.ast.expressions.Expr;

public class NumericLiteral extends Expr {
    public double value = 0d;
    public NumericLiteral(double val) {
        value = val;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
