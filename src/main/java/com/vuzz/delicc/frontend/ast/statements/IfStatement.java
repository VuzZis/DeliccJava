package com.vuzz.delicc.frontend.ast.statements;

import com.vuzz.delicc.frontend.ast.expressions.BooleanExpression;
import com.vuzz.delicc.frontend.ast.expressions.Expr;
import com.vuzz.delicc.frontend.ast.literals.BooleanLiteral;

import java.util.ArrayList;

public class IfStatement extends Stmt {

    public Program body;
    public Expr condition;
    public ArrayList<IfStatement> ifStatements = new ArrayList<>();
    public IfStatement(Program body, Expr condition) {
        this.body = body; this.condition = condition;
    }

    public IfStatement addElif(Program body, Expr condition) {
        IfStatement statement = new IfStatement(body,condition);
        ifStatements.add(statement);
        return this;
    }
    public IfStatement addElif(Program body) {
        IfStatement statement = new IfStatement(body,new BooleanLiteral(true));
        ifStatements.add(statement);
        return this;
    }

    @Override
    public String toString() {
        return "IfStatement\ncondition"+condition.toString();
    }
}
