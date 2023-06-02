package com.vuzz.delicc.frontend.ast.expressions;

public class MemberExpression extends Expr {
    public Expr object;
    public Expr property;
    public boolean isComputed;
    public MemberExpression(Expr obj, Expr prop, boolean comp) {
        object = obj;
        property = prop;
        isComputed = comp;
    }
}
