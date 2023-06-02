package com.vuzz.delicc.exceptions.node.variable;

import com.vuzz.delicc.exceptions.node.NodeError;
import com.vuzz.delicc.frontend.ast.statements.VarDeclaration;

public class FinalNotAssignedError extends NodeError {
    public FinalNotAssignedError() {
        super("Final variable isn't assigned", VarDeclaration.class);
    }
}
