package com.vuzz.delicc.core.dell1;

import com.vuzz.delicc.backend.DeliccPack;
import com.vuzz.delicc.backend.asset.NumberAsset;

public class MathPack extends DeliccPack {
    @Override
    public void initialize() {
        insertAsset("pi",new NumberAsset(Math.PI));
        insertAsset("tau",new NumberAsset(Math.PI*2));
    }
}
