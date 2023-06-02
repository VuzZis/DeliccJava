package com.vuzz.delicc.exceptions.node;

import com.vuzz.delicc.frontend.ast.statements.Program;
import com.vuzz.delicc.frontend.ast.statements.Stmt;

public class UnexpectedTokenError extends NodeError {
    public UnexpectedTokenError(String token) {
        super("Unexpected token found: "+token, Program.class);
    }
}
