package com.vuzz.delicc.exceptions.node.method;

import com.vuzz.delicc.exceptions.node.NodeError;
import com.vuzz.delicc.frontend.ast.statements.MethodDeclaration;
import com.vuzz.delicc.frontend.ast.statements.Stmt;

public class MethodNameNotDefinedError extends NodeError {

    public MethodNameNotDefinedError() {
        super("Method name not defined", MethodDeclaration.class);
    }
}
