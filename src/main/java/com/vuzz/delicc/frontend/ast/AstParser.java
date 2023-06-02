package com.vuzz.delicc.frontend.ast;

import com.vuzz.delicc.exceptions.node.UnexpectedTokenError;
import com.vuzz.delicc.frontend.Lexer;
import com.vuzz.delicc.exceptions.DeliccException;
import com.vuzz.delicc.exceptions.node.NodeError;
import com.vuzz.delicc.exceptions.node.annotation.AnnotationBodyNotPresentError;
import com.vuzz.delicc.exceptions.node.annotation.AnnotationNameNotDefined;
import com.vuzz.delicc.exceptions.node.ifstmt.IfConditionNotPresentError;
import com.vuzz.delicc.exceptions.node.method.MethodNameNotDefinedError;
import com.vuzz.delicc.exceptions.node.object.KeyNameNotDefinedError;
import com.vuzz.delicc.exceptions.node.object.MissingColonError;
import com.vuzz.delicc.exceptions.node.object.MissingCommaError;
import com.vuzz.delicc.exceptions.node.program.InvalidBodyError;
import com.vuzz.delicc.exceptions.node.variable.FinalNotAssignedError;
import com.vuzz.delicc.exceptions.node.variable.VariableNameNotDefinedError;
import com.vuzz.delicc.frontend.ast.expressions.*;
import com.vuzz.delicc.frontend.ast.literals.*;
import com.vuzz.delicc.frontend.ast.statements.*;
import com.vuzz.delicc.frontend.lexer.Token;
import com.vuzz.delicc.frontend.lexer.TokenPath;

import java.util.ArrayList;

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
        if(tokenPath.equals(Lexer.ifKeyword)) {
            return parseIfStatement();
        }
        if(tokenPath.equals(Lexer.annNewMethod)) {
            return parseMethodDeclaration();
        }
        if(tokenPath.equals(Lexer.annNewVariable) || tokenPath.equals(Lexer.annNewFinalVariable)) {
            return parseVarDeclaration();
        }
        return parseExpr();
    }

    private static Stmt parseIfStatement() throws Exception {
        eat(); expect(Lexer.charOpenParenthesis,new IfConditionNotPresentError());
        Expr expr = parseAssignmentExpr(); expect(Lexer.charClosedParenthesis, new IfConditionNotPresentError());
        Program program = (Program) parseProgramBody();
        IfStatement ifStmt = new IfStatement(program,expr);
        while (at().id.path.equals(Lexer.elifKeyword)) {
            eat(); expect(Lexer.charOpenParenthesis,new IfConditionNotPresentError());
            Expr elifExpr = parseAssignmentExpr(); expect(Lexer.charClosedParenthesis, new IfConditionNotPresentError());
            Program elif = (Program) parseProgramBody();
            ifStmt.addElif(elif,elifExpr);
        }
        if(at().id.path.equals(Lexer.elseKeyword)) {
            eat();
            Program elseProgram = (Program) parseProgramBody();
            ifStmt.addElif(elseProgram);
        }
        return ifStmt;
    }

    private static Stmt parseProgramBody() throws Exception {
        expect(Lexer.charOpenBrace,new InvalidBodyError());
        Program program = new Program();
        while (not_eof() && !at().id.path.equals(Lexer.charClosedBrace)) {
            program.body.add(parseStmt());
        }
        expect(Lexer.charClosedBrace,new InvalidBodyError());
        return program;
    }

    private static MethodDeclaration parseMethodDeclaration() throws Exception {
        eat();
        AnnotationLiteral[] annotations = parseAnnotations();
        String name = expect(Lexer.lexer.identifierPath, new MethodNameNotDefinedError()).symbol;
        Expr[] args = parseArguments();
        boolean isAbstract = !at().id.path.equals(Lexer.charOpenBrace);
        if(isAbstract)
            return new MethodDeclaration(args,name,annotations);
        Program program = (Program) parseProgramBody();
        return new MethodDeclaration(args,name,program,annotations);
    }

    private static AnnotationLiteral[] parseAnnotations() throws Exception {
        ArrayList<AnnotationLiteral> annotations = new ArrayList<>();
        while (at().id.path.equals(Lexer.charAnnotation)) {
            AnnotationLiteral literal = (AnnotationLiteral) parsePrimaryExpr();
            annotations.add(literal);
        }
        return annotations.toArray(new AnnotationLiteral[0]);
    }

    private static Expr[] parseArguments(String openChar, String closeChar, DeliccException error) throws Exception {
        expect(openChar,error);
        Expr[] arguments = at().id.path.equals(closeChar) ? new Expr[0] : parseArgumentList();
        expect(closeChar,error);
        return arguments;
    }

    private static Expr[] parseArguments() throws Exception {
        return parseArguments(Lexer.charOpenParenthesis,Lexer.charClosedParenthesis,new DeliccException("Expected parenthesis in arguments"));
    }

    private static Expr[] parseArgumentList() throws Exception {
        ArrayList<Expr> args = new ArrayList<>();
        args.add(parseAssignmentExpr());
        while(at().id.path.equals(Lexer.charComma)) {
            eat(); args.add(parseAssignmentExpr());
        }
        return args.toArray(new Expr[0]);
    }

    public static VarDeclaration parseVarDeclaration() throws Exception {
        Token newToken = eat();
        boolean isConst = newToken.id.path.equals(Lexer.annNewFinalVariable);
        AnnotationLiteral[] annotations = parseAnnotations();
        String identifier = expect(Lexer.lexer.identifierPath, new VariableNameNotDefinedError()).symbol;
        if(!at().id.path.equals(Lexer.charEquals)) {
            if(isConst)
                throw new FinalNotAssignedError();
            return new VarDeclaration(identifier, new NullLiteral(), false,annotations);
        }
        expect(Lexer.charEquals,new DeliccException("Expected equals sign"));
        return new VarDeclaration(identifier, parseExpr(), isConst,annotations);
    }

    public static Expr parseExpr() throws Exception {
        return parseAssignmentExpr();
    }

    private static Expr parseAssignmentExpr() throws Exception {
        Expr left = parseObjectExpr();
        if(at().id.path.equals(Lexer.charEquals)) {
            eat();
            return new AssignmentExpression(left,parseAssignmentExpr());
        }
        if(at().id.path.equals(Lexer.charBoolOperator)) {
            String op = eat().symbol;
            Expr right = parseAssignmentExpr();
            return new BooleanExpression(left,right,op);
        }
        return left;
    }

    private static Expr parseObjectExpr() throws Exception {
        if(!at().id.path.equals(Lexer.charOpenBrace)) {
            return parseAdditiveExpr();
        }
        eat();
        ArrayList<PropertyLiteral> props = new ArrayList<>();
        while(not_eof() && !at().id.path.equals(Lexer.charClosedBrace)) {
            Token key;
            if(at().id.equals(Lexer.lexer.identifierPath))
                key = expect(Lexer.lexer.identifierPath,new KeyNameNotDefinedError());
            else
                key = expect(Lexer.lexer.stringPath,new KeyNameNotDefinedError());
            if(at().id.path.equals(Lexer.charComma) || at().id.path.equals(Lexer.charClosedBrace)) {
                if(at().id.path.equals(Lexer.charComma)) eat();
                PropertyLiteral prop = new PropertyLiteral(key,null);
                props.add(prop);
                continue;
            }
            expect(Lexer.charColon,new MissingColonError());
            Expr val = parseExpr();
            props.add(new PropertyLiteral(key,val));
            if(!at().id.path.equals(Lexer.charClosedBrace))
                expect(Lexer.charComma,new MissingCommaError());
        }
        expect(Lexer.charClosedBrace,new NodeError("Expected closing brace after object definition", ObjectLiteral.class));
        return new ObjectLiteral(props);
    }

    private static Expr parseAdditiveExpr() throws Exception {
        Expr left = parseMultiplicativeExpr();
        while(at().symbol.equals("+") || at().symbol.equals("-")) {
            String operator = eat().symbol;
            Expr right = parseMultiplicativeExpr();
            if(left.getClass().getSimpleName().equals("NullLiteral")) left = new NumericLiteral(0);
            if(right.getClass().getSimpleName().equals("NullLiteral")) right = new NumericLiteral(0);
            left = new BinaryExpression(left,right,operator);
        }
        return left;
    }

    private static Expr parseMultiplicativeExpr() throws Exception {
        Expr left = parsePrioritiveExpr();
        while(at().symbol.equals("*") || at().symbol.equals("/") || at().symbol.equals("%")) {
            String operator = eat().symbol;
            Expr right = parsePrioritiveExpr();
            if(left.getClass().getSimpleName().equals("NullLiteral")) left = new NumericLiteral(0);
            if(right.getClass().getSimpleName().equals("NullLiteral")) right = new NumericLiteral(0);
            left = new BinaryExpression(left,right,operator);
        }
        return left;
    }

    private static Expr parsePrioritiveExpr() throws Exception {
        Expr left = parseCallMemberExpr();
        while(at().symbol.equals("**") || at().symbol.equals("//")) {
            String operator = eat().symbol;
            Expr right = parseCallMemberExpr();
            if(left.getClass().getSimpleName().equals("NullLiteral")) left = new NumericLiteral(0);
            if(right.getClass().getSimpleName().equals("NullLiteral")) right = new NumericLiteral(0);
            left = new BinaryExpression(left,right,operator);
        }
        return left;
    }

    private static Expr parseCallMemberExpr() throws Exception {
        Expr member = parseMemberExpr();
        if( at().id.path.equals(Lexer.charOpenParenthesis) )
            return parseCallExpr(member);
        return member;
    }

    private static Expr parseCallExpr(Expr caller) throws Exception {
        CallExpr expr = new CallExpr(caller,parseArguments(Lexer.charOpenParenthesis,Lexer.charClosedParenthesis,new DeliccException("Expected call arguments")));
        if(at().id.path.equals(Lexer.charOpenParenthesis))
            expr = (CallExpr) parseCallExpr(expr);
        return expr;
    }

    private static Expr parseMemberExpr() throws Exception {
        Expr obj = parsePrimaryExpr();
        while (at().id.path.equals(Lexer.charDot) || at().id.path.equals(Lexer.charOpenBracket)) {
            Token operator = eat();
            Expr property;
            boolean isComputed = !operator.id.path.equals(Lexer.charDot);
            if(isComputed) {
                property = parseExpr();
                expect(Lexer.charClosedBracket,new DeliccException("Expected closing bracket in computed member"));
            } else {
                property = parsePrimaryExpr();
                if(!property.getClass().getSimpleName().equals("Identifier"))
                    throw new DeliccException("Identifier expected in non-computed member");
            }
            obj = new MemberExpression(obj,property,isComputed);
        }
        return obj;
    }


    public static Expr parsePrimaryExpr() throws Exception {
        Token token = eat();
        String tokenPath = token.id.path;
        String tokenVal = token.symbol;
        if( tokenPath.equals(Lexer.charNumeric) ) {
            if(at().id.equals(Lexer.lexer.identifierPath)) {
                return new BinaryExpression(new NumericLiteral(Double.parseDouble(tokenVal)),new Identifier(eat().symbol),"*");
            }
            return new NumericLiteral(Double.parseDouble(tokenVal));
        }
        if( tokenPath.equals(Lexer.lexer.stringPath.path))
            return new StringLiteral(tokenVal);
        if( tokenPath.equals(Lexer.lexer.identifierPath.path))
            return new Identifier(tokenVal);
        if( tokenPath.equals(Lexer.charColon) ) {
            Expr[] arguments = parseArguments(Lexer.charOpenParenthesis,Lexer.charClosedParenthesis,
                    new DeliccException("Expected parenthesis in arguments"));;
            expect(Lexer.charLambda,new DeliccException("Expected lambda arrow ->"));
            Program body = (Program) parseProgramBody();
            return new LambdaLiteral(arguments,body);
        }
        if( tokenPath.equals(Lexer.charOpenParenthesis)) {
            Expr expr = parseExpr();
            expect(Lexer.charClosedParenthesis,new DeliccException("Expected closing parenthesis"));
            return expr;
        }
        if( tokenPath.equals(Lexer.charBinOperator)) {
            if(!tokenVal.equals("-")) throw new UnexpectedTokenError(tokenVal);
            Expr expr = parsePrimaryExpr();
            return new BinaryExpression(new NumericLiteral(0),expr,tokenVal);
        }
        if( tokenPath.equals(Lexer.charAnnotation) ) {
            String name = expect(Lexer.lexer.identifierPath,new AnnotationNameNotDefined()).symbol;
            Expr[] args = parseArguments(Lexer.charOpenBrace,Lexer.charClosedBrace,new AnnotationBodyNotPresentError());
            return new AnnotationLiteral(args,name);
        }

        throw new UnexpectedTokenError(tokenVal);
    }

    private static Token at() {
        return pos != tokens.length ? tokens[pos] : new Token(TokenPath.from("token","nil"),"");
    }

    private static Token eat() {
        return pos != tokens.length ? tokens[pos++] : new Token(TokenPath.from("token","nil"),"");
    }

    private static Token expect(TokenPath id,DeliccException msg) throws Exception {
        if(pos >= tokens.length) throw msg;
        Token token = tokens[pos++];
        if(token == null || !token.id.equals(id)) {
            throw msg;
        }
        return token;
    }

    private static Token expect(String id, DeliccException msg) throws Exception { return expect(new TokenPath(id),msg);}

    private static boolean not_eof() {
        return pos != tokens.length;
    }
}
