package com.vuzz.delicc.exceptions.node.object;

import com.vuzz.delicc.exceptions.node.NodeError;
import com.vuzz.delicc.frontend.ast.literals.ObjectLiteral;
import com.vuzz.delicc.frontend.ast.statements.Stmt;

public class MissingColonError extends NodeError {

    public MissingColonError() {
        super("Missing colon after key identifier", ObjectLiteral.class);
    }
}
