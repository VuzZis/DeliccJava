package com.vuzz.delicc.frontend.ast.expressions;

public class BooleanExpression extends Expr {
    public Expr left;
    public Expr right;
    public String operator;
    public BooleanExpression(Expr l, Expr r, String bolnop) {
        left = l;
        right = r;
        operator = bolnop;
    }

    @Override
    public String toString() {
        return "("+left.toString()+" "+operator+" "+right.toString()+")";
    }
}
