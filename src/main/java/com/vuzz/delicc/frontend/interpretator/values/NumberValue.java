package com.vuzz.delicc.frontend.interpretator.values;

public class NumberValue extends RuntimeValue {
    public double value;
    public NumberValue(double value) {
        this.value = value;
    }

    @Override
    public NumberValue toNumeric() {
        return this;
    }

    @Override
    public StringValue toStringy() {
        return new StringValue(String.valueOf(value));
    }
}
