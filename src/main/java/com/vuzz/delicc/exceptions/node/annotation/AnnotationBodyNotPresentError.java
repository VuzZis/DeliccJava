package com.vuzz.delicc.exceptions.node.annotation;

import com.vuzz.delicc.exceptions.node.NodeError;
import com.vuzz.delicc.frontend.ast.literals.AnnotationLiteral;

public class AnnotationBodyNotPresentError extends NodeError {

    public AnnotationBodyNotPresentError() {
        super("Annotation body not present", AnnotationLiteral.class);
    }
}
