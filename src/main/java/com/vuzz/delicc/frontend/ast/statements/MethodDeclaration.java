package com.vuzz.delicc.frontend.ast.statements;

import com.vuzz.delicc.frontend.ast.expressions.Expr;
import com.vuzz.delicc.frontend.ast.literals.AnnotationLiteral;

public class MethodDeclaration extends Stmt {
    public Expr[] arguments;
    public Program body;
    public String name;
    public boolean isAbstract;
    public AnnotationLiteral[] annotations;
    public MethodDeclaration(Expr[] args, String name, Program body, AnnotationLiteral[] annotations) {
        this.arguments = args;
        this.name = name;
        this.body = body;
        this.isAbstract = false;

        this.annotations = annotations;
    }

    public MethodDeclaration(Expr[] args, String name,AnnotationLiteral[] annotations) {
        this.arguments = args;
        this.name = name;
        this.body = new Program();
        this.isAbstract = true;
        this.annotations = annotations;
    }

    @Override
    public String toString() {
        StringBuilder annotationsStr = new StringBuilder();
        for (AnnotationLiteral annotation : annotations) {
            annotationsStr.append(annotation.toString()).append("; ");
        }
        return "MethodDeclaration\n  name:\n    "+name+"\n  isAbstract:\n    "+isAbstract+"\n  annotations:\n    "+annotationsStr;
    }
}
