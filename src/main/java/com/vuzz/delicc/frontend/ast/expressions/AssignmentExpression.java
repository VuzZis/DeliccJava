package com.vuzz.delicc.frontend.ast.expressions;

public class AssignmentExpression extends Expr {
    public Expr left;
    public Expr value;

    public AssignmentExpression(Expr left, Expr val) {
        this.left = left;
        this.value = val;
    }

    @Override
    public String toString() {
        return "AssignmentExpression\n  name:\n    "+left.toString()+"\n  value:\n    "+value;
    }
}
