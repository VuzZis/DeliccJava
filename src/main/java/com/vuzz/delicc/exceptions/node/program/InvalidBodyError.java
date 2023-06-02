package com.vuzz.delicc.exceptions.node.program;

import com.vuzz.delicc.exceptions.node.NodeError;
import com.vuzz.delicc.frontend.ast.statements.Program;
import com.vuzz.delicc.frontend.ast.statements.Stmt;

public class InvalidBodyError extends NodeError {

    public InvalidBodyError() {
        super("Invalid program body", Program.class);
    }
}
