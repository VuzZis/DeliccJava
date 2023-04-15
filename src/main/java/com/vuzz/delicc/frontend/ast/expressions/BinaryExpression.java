package com.vuzz.delicc.frontend.ast.expressions;

public class BinaryExpression extends Expr {
    public Expr left;
    public Expr right;
    public String operator;
    public BinaryExpression(Expr l, Expr r, String binop) {
        left = l;
        right = r;
        operator = binop;
    }

    @Override
    public String toString() {
        return "("+left.toString()+operator+right.toString()+")";
    }
}
