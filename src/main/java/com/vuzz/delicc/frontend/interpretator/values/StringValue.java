package com.vuzz.delicc.frontend.interpretator.values;

public class StringValue extends RuntimeValue {
    public String value;
    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public RuntimeValue toNumeric() {
        return this;
    }

    @Override
    public StringValue toStringy() {
        return this;
    }
}
