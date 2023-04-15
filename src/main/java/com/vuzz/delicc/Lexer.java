package com.vuzz.delicc;

import com.vuzz.delicc.frontend.lexer.LexerLang;
import com.vuzz.delicc.frontend.lexer.Token;
import com.vuzz.delicc.frontend.lexer.TokenPath;

public class Lexer {

    public static final LexerLang lexer = new LexerLang();

    public static final String annNewVariable = lexer.registerKeyword(TokenPath.from("annotation","var/new"),"@new");
    public static final String annNewFinalVariable = lexer.registerKeyword(TokenPath.from("annotation","var/final/new"),"@fnew");

    public static final String boolTrue = lexer.registerKeyword(TokenPath.from("value","boolean"),"true");
    public static final String boolFalse = lexer.registerKeyword(TokenPath.from("value","boolean"),"false");

    public static final String charBinOperator = lexer.registerChar(TokenPath.from("operator","binary"),"+","-","*","/");
    public static final String charEquals = lexer.registerChar(TokenPath.from("operator","program/equals"),"=");

    public static final String charDot = lexer.registerChar(TokenPath.from("symbol","dot"),".");

    public static final String charOpenParenthesis = lexer.registerChar(TokenPath.from("parenthesis","open"),"(");
    public static final String charClosedParenthesis = lexer.registerChar(TokenPath.from("parenthesis","closed"),")");
    public static final String charOpenBracket = lexer.registerChar(TokenPath.from("bracket","open"),"[");
    public static final String charClosedBracket = lexer.registerChar(TokenPath.from("bracket","closed"),"]");
    public static final String charSingleQuote = lexer.registerChar(TokenPath.from("quote","single"),"'");
    public static final String charDoubleQuote = lexer.registerChar(TokenPath.from("quote","double"),"\"");

    public static final String charNumeric = lexer.registerChar(TokenPath.from("value","numeric"),"0","1","2","3","4","5","6","7","8","9");

    public static final String charSkippable = lexer.registerSkippable(";","\n","\r"," ");

    public static Token[] parseString(String str) { return lexer.parseString(str); }
}
