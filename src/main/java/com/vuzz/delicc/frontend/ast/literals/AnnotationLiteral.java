package com.vuzz.delicc.frontend.ast.literals;

import com.vuzz.delicc.frontend.ast.expressions.Expr;

public class AnnotationLiteral extends Expr {
    public Expr[] arguments;
    public String name;
    public static final String kind = "Annotation";
    public AnnotationLiteral(Expr[] args, String name) {
        this.name = name;
        this.arguments = args;
    }

    @Override
    public String toString() {
        return "@"+name+"{"+"}";
    }
    
}
