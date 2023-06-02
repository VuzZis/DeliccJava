package com.vuzz.delicc.frontend.ast.literals;

import com.vuzz.delicc.frontend.ast.expressions.Expr;

import java.util.ArrayList;

public class ObjectLiteral extends Expr {
    public ArrayList<PropertyLiteral> props = new ArrayList<>();
    public ObjectLiteral(ArrayList<PropertyLiteral> props) {
        this.props = props;
    }

    @Override
    public String toString() {
        final String[] str = {""};
        props.forEach((e) -> {
            str[0] += ""+e.key.symbol+" : "+e.value+", ";
        });
        return "{ "+str[0]+" }";
    }
}
