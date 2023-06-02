package com.vuzz.delicc.backend.asset;

import com.vuzz.delicc.backend.AssetType;
import com.vuzz.delicc.backend.DeliccAsset;

public class NumberAsset extends DeliccAsset {

    double num = 0;
    public NumberAsset(double val) {
        num = val;
    }

    @Override
    public AssetType getType() {
        return AssetType.INTEGER;
    }

    @Override
    public Object getValue() {
        return num;
    }
}
