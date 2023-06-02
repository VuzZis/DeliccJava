package com.vuzz.delicc.backend;

import com.vuzz.delicc.frontend.interpretator.Environment;

public abstract class DeliccPack extends DeliccAsset {

    public abstract void initialize();
    public Environment env = new Environment();
    public void insertAsset(String id, DeliccAsset asset) {
        env.insertAsset(id,asset);
    }

    @Override
    public Object getValue() {
        return env;
    }

    @Override
    public AssetType getType() {
        return AssetType.PACK;
    }

}
