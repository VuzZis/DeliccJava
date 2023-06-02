package com.vuzz.delicc.exceptions;

public class DeliccException extends Exception {
    public String error;
    public DeliccException(String error) {
        this.error = error;
    }



    @Override
    public String getMessage() {
        return "DeliccException: "+error;
    }
}
