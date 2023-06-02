package com.vuzz.delicc.frontend;

import com.vuzz.delicc.frontend.lexer.LexerLang;
import com.vuzz.delicc.frontend.lexer.Token;
import com.vuzz.delicc.frontend.lexer.TokenPath;

public class Lexer {

    public static final LexerLang lexer = new LexerLang();

    public static final String annNewVariable = lexer.registerKeyword(TokenPath.from("annotation","var/new"),"var");
    public static final String annNewFinalVariable = lexer.registerKeyword(TokenPath.from("annotation","var/final/new"),"final");
    public static final String annNewMethod = lexer.registerKeyword(TokenPath.from("annotation","var/method/new"),"method");
    public static final String annReturn = lexer.registerKeyword(TokenPath.from("annotation","return"),"return");

    public static final String charAnnotation = lexer.registerChar(TokenPath.from("annotation","symbol"),"@");

    public static final String charLambda = lexer.registerDoubleChar(TokenPath.from("symbol","lambda"),"-",">");

    public static final String ifKeyword = lexer.registerKeyword(TokenPath.from("if","if"),"if");
    public static final String elifKeyword = lexer.registerKeyword(TokenPath.from("if","elif"),"elif");
    public static final String elseKeyword = lexer.registerKeyword(TokenPath.from("if","else"),"else");

    public static final String boolTrue = lexer.registerKeyword(TokenPath.from("value","boolean"),"true");
    public static final String boolFalse = lexer.registerKeyword(TokenPath.from("value","boolean"),"false");

    public static final String charBoolOperator = lexer.registerDoubleChar(TokenPath.from("operator","boolean"),"&","&");
    public static final String charBoolOperator2 = lexer.registerDoubleChar(TokenPath.from("operator","boolean"),"|","|");
    public static final String charBoolOperator3 = lexer.registerDoubleChar(TokenPath.from("operator","boolean"),"!","=");
    public static final String charBoolOperator4 = lexer.registerDoubleChar(TokenPath.from("operator","boolean"),"=","=");
    public static final String charBoolOperator5 = lexer.registerDoubleChar(TokenPath.from("operator","boolean"),">","=");
    public static final String charBoolOperator6 = lexer.registerDoubleChar(TokenPath.from("operator","boolean"),"<","=");
    public static final String charBoolOperator7 = lexer.registerChar(TokenPath.from("operator","boolean"),">","<","!");

    public static final String charBinOperator2 = lexer.registerDoubleChar(TokenPath.from("operator","binary"),"*","*");
    public static final String charBinOperator1 = lexer.registerDoubleChar(TokenPath.from("operator","binary"),"/","/");
    public static final String charBinOperator = lexer.registerChar(TokenPath.from("operator","binary"),"+","-","*","/","%");

    public static final String charEquals = lexer.registerChar(TokenPath.from("operator","program/equals"),"=");

    public static final String charDot = lexer.registerChar(TokenPath.from("symbol","dot"),".");
    public static final String charComma = lexer.registerChar(TokenPath.from("symbol","comma"),",");
    public static final String charColon = lexer.registerChar(TokenPath.from("symbol","colon"),":");

    public static final String charOpenParenthesis = lexer.registerChar(TokenPath.from("parenthesis","open"),"(");
    public static final String charClosedParenthesis = lexer.registerChar(TokenPath.from("parenthesis","closed"),")");
    public static final String charOpenBracket = lexer.registerChar(TokenPath.from("bracket","open"),"[");
    public static final String charClosedBracket = lexer.registerChar(TokenPath.from("bracket","closed"),"]");
    public static final String charOpenBrace = lexer.registerChar(TokenPath.from("brace","open"),"{");
    public static final String charClosedBrace = lexer.registerChar(TokenPath.from("brace","closed"),"}");
    public static final String charSingleQuote = lexer.registerChar(TokenPath.from("quote","single"),"'");
    public static final String charDoubleQuote = lexer.registerChar(TokenPath.from("quote","double"),"\"");

    public static final String charNumeric = lexer.registerChar(TokenPath.from("value","numeric"),"0","1","2","3","4","5","6","7","8","9");

    public static final String charSkippable = lexer.registerSkippable(";","\n","\r"," ");

    public static Token[] parseString(String str) { return lexer.parseString(str); }
}
