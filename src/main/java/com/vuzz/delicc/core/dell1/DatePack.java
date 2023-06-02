package com.vuzz.delicc.core.dell1;

import com.vuzz.delicc.backend.DeliccPack;
import com.vuzz.delicc.backend.asset.NumberAsset;

import java.util.Date;

public class DatePack extends DeliccPack {
    @Override
    public void initialize() {
        //insertAsset("millis", new NumberAsset(new Date().getTime()));
    }
}
