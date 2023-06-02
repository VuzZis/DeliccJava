package com.vuzz.delicc.frontend.interpretator.values;

public abstract class RuntimeValue {
    public double value = 0;
    public abstract RuntimeValue toNumeric();
    public abstract RuntimeValue toStringy();
}
