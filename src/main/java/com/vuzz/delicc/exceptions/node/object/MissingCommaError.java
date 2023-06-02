package com.vuzz.delicc.exceptions.node.object;

import com.vuzz.delicc.exceptions.node.NodeError;
import com.vuzz.delicc.frontend.ast.literals.ObjectLiteral;
import com.vuzz.delicc.frontend.ast.statements.Stmt;

public class MissingCommaError extends NodeError {
    public MissingCommaError() {
        super("Missing comma after property definition", ObjectLiteral.class);
    }
}
