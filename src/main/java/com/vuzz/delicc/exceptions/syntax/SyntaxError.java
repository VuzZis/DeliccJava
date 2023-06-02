package com.vuzz.delicc.exceptions.syntax;

import com.vuzz.delicc.exceptions.DeliccException;

public class SyntaxError extends DeliccException {

    public SyntaxError(String error) {
        super(error);
    }

    @Override
    public String getMessage() {
        return "SyntaxError: "+error;
    }
}
