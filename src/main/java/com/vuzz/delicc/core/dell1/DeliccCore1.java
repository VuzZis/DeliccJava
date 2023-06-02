package com.vuzz.delicc.core.dell1;

import com.vuzz.delicc.backend.DeliccPack;
import com.vuzz.delicc.backend.asset.NumberAsset;

public class DeliccCore1 extends DeliccPack {


    @Override
    public void initialize() {
        //Inserting basic values
        insertAsset("nan",new NumberAsset(Double.NaN));
        insertAsset("inf",new NumberAsset(Double.MAX_VALUE));

        //Adding packs
        insertAsset("Math",new MathPack());
    }

}
