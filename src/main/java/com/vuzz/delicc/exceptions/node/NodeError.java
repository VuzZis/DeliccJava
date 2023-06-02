package com.vuzz.delicc.exceptions.node;

import com.vuzz.delicc.exceptions.syntax.SyntaxError;
import com.vuzz.delicc.frontend.ast.statements.Stmt;

public class NodeError extends SyntaxError {

    public NodeError(String error, Class<? extends Stmt> astNode) {
        super(error);
    }

    @Override
    public String getMessage() {
        return "NodeError: "+error;
    }
}
