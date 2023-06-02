package com.vuzz.delicc.frontend.interpretator.values;

public class NullValue extends RuntimeValue {

    @Override
    public NumberValue toNumeric() {
        return new NumberValue(0);
    }

    @Override
    public StringValue toStringy() {
        return new StringValue("null");
    }
}
