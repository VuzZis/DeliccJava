package com.vuzz.delicc.frontend.ast.statements;

import com.vuzz.delicc.frontend.ast.expressions.Expr;

public class VarDeclaration extends Stmt {
    public boolean isFinal = false;
    public String name = "";
    public Expr value;
    public VarDeclaration(String name,Expr val, boolean isFinal) {
        this.isFinal = isFinal;
        this.name = name;
        this.value = val;
    }

    @Override
    public String toString() {
        return "VariableDeclaration\n  name:\n    "+name+"\n  value:\n    "+value+"\n  final:\n    "+isFinal;
    }
}
