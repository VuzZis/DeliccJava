package com.vuzz.delicc.exceptions.node.ifstmt;

import com.vuzz.delicc.exceptions.node.NodeError;
import com.vuzz.delicc.frontend.ast.statements.IfStatement;
import com.vuzz.delicc.frontend.ast.statements.Stmt;

public class IfConditionNotPresentError extends NodeError {

    public IfConditionNotPresentError() {
        super("Condition of IF not present", IfStatement.class);
    }
}
