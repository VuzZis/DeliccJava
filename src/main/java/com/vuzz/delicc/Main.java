package com.vuzz.delicc;

import com.vuzz.delicc.frontend.ast.AstParser;
import com.vuzz.delicc.frontend.ast.statements.Program;
import com.vuzz.delicc.frontend.lexer.Token;

public class Main {

    public static void main(String[] args) throws Exception {
        String testString = "@new testVarA = \"test\"*5+2; testVarA = testVarA*2";
        Token[] tokens = Lexer.parseString(testString);
        Program program = AstParser.parse(tokens);
        System.out.println(program.toString());
    }
}