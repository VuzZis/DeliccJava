package com.vuzz.delicc.exceptions.node.variable;

import com.vuzz.delicc.exceptions.node.NodeError;
import com.vuzz.delicc.frontend.ast.statements.Stmt;
import com.vuzz.delicc.frontend.ast.statements.VarDeclaration;

public class VariableNameNotDefinedError extends NodeError {

    public VariableNameNotDefinedError() {
        super("Variable name not defined", VarDeclaration.class);
    }
}
