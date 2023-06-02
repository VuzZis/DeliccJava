package com.vuzz.delicc.exceptions.node.object;

import com.vuzz.delicc.exceptions.node.NodeError;
import com.vuzz.delicc.frontend.ast.literals.ObjectLiteral;
import com.vuzz.delicc.frontend.ast.statements.Stmt;

public class KeyNameNotDefinedError extends NodeError {
    public KeyNameNotDefinedError() {
        super("Key identifier not defined", ObjectLiteral.class);
    }
}
