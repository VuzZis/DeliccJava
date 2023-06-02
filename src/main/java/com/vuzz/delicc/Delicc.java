package com.vuzz.delicc;

import com.vuzz.delicc.frontend.Lexer;
import com.vuzz.delicc.frontend.ast.AstParser;
import com.vuzz.delicc.frontend.ast.statements.Program;
import com.vuzz.delicc.frontend.interpretator.Environment;
import com.vuzz.delicc.frontend.interpretator.Interpretator;
import com.vuzz.delicc.frontend.interpretator.values.RuntimeValue;
import com.vuzz.delicc.frontend.lexer.Token;

public class Delicc {

    public RuntimeValue evaluate(Environment environment, Program program) throws Exception {
        return Interpretator.evaluate(program,environment);
    }

    public RuntimeValue evaluate(Environment environment, Token[] code) throws Exception {
        return evaluate(environment, astNodes(code));
    }
    public RuntimeValue evaluate(Environment environment, String code) throws Exception {
        return evaluate(environment, lexerTokens(code));
    }

    public Token[] lexerTokens(String code) throws Exception {
        return Lexer.parseString(code);
    }

    public Program astNodes(Token[] code) throws Exception {
        return AstParser.parse(code);
    }

    public Program astNodes(String code) throws Exception {
        return astNodes(lexerTokens(code));
    }

}
