package com.vuzz.delicc.frontend.ast.expressions;

public class CallExpr extends Expr {
    public Expr caller;
    public Expr[] args;

    public CallExpr(Expr caller, Expr[] args) {
        this.caller = caller;
        this.args = args;
    }
}
