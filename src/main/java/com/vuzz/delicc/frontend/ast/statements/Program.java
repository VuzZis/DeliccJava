package com.vuzz.delicc.frontend.ast.statements;

import java.util.ArrayList;

public class Program extends Stmt {
    public ArrayList<Stmt> body = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder strVal = new StringBuilder("Program \n");
        body.forEach((stmt) -> {
            strVal.append(" "+stmt.toString()+"\n");
        });
        return strVal.toString();
    }
}
