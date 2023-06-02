package com.vuzz.delicc.frontend.ast.literals;

import com.vuzz.delicc.frontend.ast.expressions.Expr;
import com.vuzz.delicc.frontend.ast.statements.Program;

public class LambdaLiteral extends Expr {
    public Expr[] arguments;
    public Program body;

    public LambdaLiteral(Expr[] args, Program program) {
        arguments = args; body = program;
    }

    @Override
    public String toString() {
        return "lambda";
    }
}
