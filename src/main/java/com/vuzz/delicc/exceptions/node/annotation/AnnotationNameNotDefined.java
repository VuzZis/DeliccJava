package com.vuzz.delicc.exceptions.node.annotation;

import com.vuzz.delicc.exceptions.node.NodeError;
import com.vuzz.delicc.frontend.ast.literals.AnnotationLiteral;
import com.vuzz.delicc.frontend.ast.statements.Stmt;

public class AnnotationNameNotDefined extends NodeError {

    public AnnotationNameNotDefined() {
        super("Annotation name not defined", AnnotationLiteral.class);
    }
}
