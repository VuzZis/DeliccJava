package com.vuzz.delicc.frontend.interpretator;

import com.vuzz.delicc.Delicc;
import com.vuzz.delicc.backend.DeliccAsset;
import com.vuzz.delicc.exceptions.DeliccException;
import com.vuzz.delicc.frontend.interpretator.values.NumberValue;
import com.vuzz.delicc.frontend.interpretator.values.RuntimeValue;
import com.vuzz.delicc.frontend.interpretator.values.StringValue;

import java.util.HashMap;

public class Environment extends RuntimeValue {

    public Environment parent;
    public HashMap<String,Object> variables = new HashMap<>();
    public HashMap<String,Object> constants = new HashMap<>();
    public Environment(Environment parent) {
        this.parent = parent;
    }
    public Environment() {
        this.parent = Environment.NIL;
    }

    public RuntimeValue declareVar(String varName, RuntimeValue value, boolean isConst ) throws DeliccException {
        if(variables.containsKey(varName))
            throw new DeliccException("Cannot declare a variable that already declared"); //TODO: error
        (isConst ? constants : variables).put(varName,value);
        return value;
    }

    public RuntimeValue assignVar(String varName, RuntimeValue value) throws DeliccException {
        Environment env = resolve(varName);
        env.variables.put(varName,value);
        return value;
    }

    public RuntimeValue lookup(String varName) throws DeliccException {
        Environment env = resolve(varName);
        return (RuntimeValue) env.variables.get(varName);
    }

    public Environment resolve(String varName) throws DeliccException {
        if(variables.containsKey(varName))
            return this;
        if(parent == null)
            throw new DeliccException("Cannot resolve variable"); //TODO:ERROR
        return parent.resolve(varName);
    }

    public Environment insertAsset(String id, DeliccAsset asset) {
        constants.put(id,asset.deliccicate());
        return this;
    }

    public Environment useCore(DeliccAsset asset) {
        return (Environment) asset.deliccicate();
    }

    public static Environment NIL = new Environment(null);

    @Override
    public NumberValue toNumeric() {
        return null;
    }

    @Override
    public StringValue toStringy() {
        return null;
    }
}
