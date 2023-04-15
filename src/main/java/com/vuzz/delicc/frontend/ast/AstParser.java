package com.vuzz.delicc.frontend.ast;

import com.vuzz.delicc.Lexer;
import com.vuzz.delicc.frontend.ast.expressions.AssignmentExpression;
import com.vuzz.delicc.frontend.ast.expressions.BinaryExpression;
import com.vuzz.delicc.frontend.ast.expressions.Expr;
import com.vuzz.delicc.frontend.ast.literals.NullLiteral;
import com.vuzz.delicc.frontend.ast.literals.NumericLiteral;
import com.vuzz.delicc.frontend.ast.literals.StringLiteral;
import com.vuzz.delicc.frontend.ast.statements.Program;
import com.vuzz.delicc.frontend.ast.statements.Stmt;
import com.vuzz.delicc.frontend.ast.statements.VarDeclaration;
import com.vuzz.delicc.frontend.lexer.Token;
import com.vuzz.delicc.frontend.lexer.TokenPath;

public class AstParser {
    public static Token[] tokens;
    public static int pos = 0;

    public static Program parse(Token[] tokens) throws Exception {
        AstParser.tokens = tokens;
        pos = 0;
        Program program = new Program();
        while(not_eof()) {
            program.body.add(parseStmt());
        }
        return program;
    }

    private static Stmt parseStmt() throws Exception {
        Token token = at();
        String tokenPath = token.id.path;
        if(tokenPath.equals(Lexer.annNewVariable) || tokenPath.equals(Lexer.annNewFinalVariable)) {
            return parseVarDeclaration();
        }
        return parseExpr();
    }

    public static VarDeclaration parseVarDeclaration() throws Exception {
        Token newToken = eat();
        boolean isConst = newToken.symbol.equals("@fnew");
        String identifier = expect(Lexer.lexer.identifierPath, "Expected variable identifier.").symbol;
        if(!at().id.path.equals(Lexer.charEquals)) {
            if(isConst)
                throw new Exception("Final variable isn't declared: "+identifier);
            return new VarDeclaration(identifier, new NullLiteral(), isConst);
        }
        expect(Lexer.charEquals,"Expected equal sign.");
        return new VarDeclaration(identifier, parseExpr(), isConst);
    }

    public static Expr parseExpr() {
        return parseAssignmentExpr();
    }

    private static Expr parseAssignmentExpr() {
        Expr left = parseObjectExpr();
        if(at().id.path.equals(Lexer.charEquals)) {
            eat();
            return new AssignmentExpression(left,parseAssignmentExpr());
        }
        return left;
    }

    private static Expr parseObjectExpr() {
        return parseAdditiveExpr();
    }

    private static Expr parseAdditiveExpr() {
        Expr left = parseMultiplicativeExpr();
        while(at().symbol.equals("+") || at().symbol.equals("-")) {
            String operator = eat().symbol;
            Expr right = parseMultiplicativeExpr();
            if(left.getClass().getSimpleName() == "NullLiteral") left = new NumericLiteral(0);
            if(right.getClass().getSimpleName() == "NullLiteral") right = new NumericLiteral(0);
            left = new BinaryExpression(left,right,operator);
        }
        return left;
    }

    private static Expr parseMultiplicativeExpr() {
        Expr left = parseCallMemberExpr();
        while(at().symbol.equals("*") || at().symbol.equals("/") || at().symbol.equals("%")) {
            String operator = eat().symbol;
            Expr right = parseCallMemberExpr();
            if(left.getClass().getSimpleName() == "NullLiteral") left = new NumericLiteral(0);
            if(right.getClass().getSimpleName() == "NullLiteral") right = new NumericLiteral(0);
            left = new BinaryExpression(left,right,operator);
        }
        return left;
    }

    private static Expr parseCallMemberExpr() {
        return parseMemberExpr();
    }

    private static Expr parseMemberExpr() {
        return parsePrimaryExpr();
    }


    public static Expr parsePrimaryExpr() {
        Token token = eat();
        String tokenPath = token.id.path;
        String tokenVal = token.symbol;
        if( tokenPath.equals(Lexer.charNumeric) )
            return new NumericLiteral(Double.parseDouble(tokenVal));
        if( tokenPath.equals(Lexer.lexer.stringPath.path))
            return new StringLiteral(tokenVal);
        return new NullLiteral();
    }

    private static Token at() {
        return pos != tokens.length ? tokens[pos] : new Token(TokenPath.from("token","nil"),"");
    }

    private static Token eat() {
        return tokens[pos++];
    }

    private static Token expect(TokenPath id,String msg) throws Exception {
        Token token = tokens[pos++];
        if(token == null || !token.id.equals(id)) {
            throw new Exception(msg);
        }
        return token;
    }

    private static Token expect(String id,String msg) throws Exception { return expect(new TokenPath(id),msg);}

    private static boolean not_eof() {
        return pos != tokens.length;
    }
}
