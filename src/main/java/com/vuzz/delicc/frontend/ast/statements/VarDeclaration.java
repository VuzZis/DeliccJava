package com.vuzz.delicc.frontend.ast.statements;

import com.vuzz.delicc.frontend.ast.expressions.Expr;
import com.vuzz.delicc.frontend.ast.literals.AnnotationLiteral;

public class VarDeclaration extends Stmt {
    public boolean isFinal = false;
    public String name = "";
    public Expr value;
    public AnnotationLiteral[] annotations;
    public VarDeclaration(String name, Expr val, boolean isFinal, AnnotationLiteral[] annotations) {
        this.isFinal = isFinal;
        this.name = name;
        this.value = val;
        this.annotations = annotations;
    }

    @Override
    public String toString() {
        StringBuilder annotationsStr = new StringBuilder();
        for (AnnotationLiteral annotation : annotations) {
            annotationsStr.append(annotation.toString()).append("; ");
        }
        return "VariableDeclaration\n  name:\n    "+name+"\n  value:\n    "+value+"\n  final:\n    "+isFinal+"\n  annotations:\n    "+annotationsStr;
    }
}
