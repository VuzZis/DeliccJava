package com.vuzz.delicc.backend;

import com.vuzz.delicc.frontend.interpretator.Environment;
import com.vuzz.delicc.frontend.interpretator.values.*;

public abstract class DeliccAsset {

    public RuntimeValue deliccicate() {
        AssetType type = getType();
        switch (type) {
            case INTEGER:
                return new NumberValue((Double) getValue());
            case STRING:
                return new StringValue((String) getValue());
            case PACK:
                ((DeliccPack) this).initialize();
                return (Environment) getValue();
            case METHOD:
                break;
            case OBJECT:
                break;
            case BOOLEAN:
                break;
            case ANNOTATION:
                break;
        }
        return new NullValue();
    };

    public abstract AssetType getType();
    public abstract Object getValue();

}
