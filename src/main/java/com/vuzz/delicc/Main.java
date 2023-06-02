package com.vuzz.delicc;

import com.vuzz.delicc.core.dell1.DeliccCore1;
import com.vuzz.delicc.exceptions.DeliccException;
import com.vuzz.delicc.frontend.interpretator.Environment;
import com.vuzz.delicc.frontend.interpretator.values.RuntimeValue;
import com.vuzz.delicc.frontend.interpretator.values.StringValue;

@SuppressWarnings("ALL")
public class Main {

    public static void main(String[] args) throws Exception {
        try {
            String testString = "var a = \"Hello World!\\n\"*-5;";
            Delicc deliccLang = new Delicc();
            Environment environment = new Environment().useCore(new DeliccCore1());
            RuntimeValue finished = deliccLang.evaluate(environment,testString);
            System.out.println( ( (StringValue) finished.toStringy() ).value );
        } catch (DeliccException e) {
            System.out.println(e.getMessage());
        } finally {

        }
    }
}