package com.vuzz.delicc.frontend.interpretator;

import com.vuzz.delicc.exceptions.DeliccException;
import com.vuzz.delicc.frontend.ast.expressions.*;
import com.vuzz.delicc.frontend.ast.literals.NumericLiteral;
import com.vuzz.delicc.frontend.ast.literals.StringLiteral;
import com.vuzz.delicc.frontend.ast.statements.Program;
import com.vuzz.delicc.frontend.ast.statements.Stmt;
import com.vuzz.delicc.frontend.ast.statements.VarDeclaration;
import com.vuzz.delicc.frontend.interpretator.values.NullValue;
import com.vuzz.delicc.frontend.interpretator.values.NumberValue;
import com.vuzz.delicc.frontend.interpretator.values.RuntimeValue;
import com.vuzz.delicc.frontend.interpretator.values.StringValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class Interpretator {




    public static RuntimeValue evaluate(Stmt astNode, Environment env) throws DeliccException {
        String nodeKind = astNode.getClass().getSimpleName();
        switch (nodeKind) {
            case "NumericLiteral":
                return new NumberValue(( (NumericLiteral) astNode ).value);
            case "StringLiteral":
                return new StringValue(( (StringLiteral) astNode ).value);
            case "Identifier":
                return evaluateIdentifier(astNode,env);
            case "BinaryExpression":
                return evalBinaryExpr(astNode,env);
            case "VarDeclaration":
                return evalVarDeclaration(astNode,env);
            case "AssignmentExpression":
                return evalAssignment(astNode,env);
                //TODO: ObjectLiteral
            case "CallExpr":
                return evalCallExpr(astNode,env);
            case "Program":
                return evalProgram(astNode,env);
            default:
                return new NullValue();
        }
    }

    private static RuntimeValue evalProgram(Stmt astNode, Environment env) {
        final RuntimeValue[] lastEvaluated = {new NullValue()};
        Program program = ((Program) astNode);
        program.body.forEach((stmt) -> {
            try {
                lastEvaluated[0] = evaluate(stmt,env);
            } catch (DeliccException e) {
                throw new RuntimeException(e);
            }
        });
        return lastEvaluated[0];
    }

    private static RuntimeValue evalCallExpr(Stmt astNode, Environment env) throws DeliccException {
        CallExpr expr = (CallExpr) astNode;
        RuntimeValue[] args = map(expr.args,(a) -> {
            try {
                return evaluate(a,env);
            } catch (DeliccException e) {
                throw new RuntimeException(e);
            }
        });
        RuntimeValue fn = evaluate(expr.caller,env);
        return new NullValue();
    }

    private static <T,B> B[] map(T[] arr, Function<T,B> call) {
        ArrayList<T> arrList = (ArrayList<T>) Arrays.asList(arr);
        ArrayList<B> newList = new ArrayList<>();
        arrList.forEach(i -> {
            newList.add(call.apply(i));
        });
        return (B[]) newList.toArray();
    }

    private static RuntimeValue evalAssignment(Stmt astNode, Environment env) throws DeliccException {
        AssignmentExpression expr = ((AssignmentExpression) astNode);
        String varKind = expr.left.getClass().getSimpleName();
        if(!varKind.equals("Identifier") && !varKind.equals("StringLiteral"))
            throw new DeliccException("Cannot assign a variable with name of"); //TODO:ERROR
        String varName = (varKind.equals("Identifier")) ? ((Identifier) expr.left).symbol : ((StringLiteral) expr.left).value;
        return env.assignVar(varName,evaluate(expr.value,env));
    }

    private static RuntimeValue evalVarDeclaration(Stmt astNode, Environment env) throws DeliccException {
        VarDeclaration dec = ((VarDeclaration) astNode);
        RuntimeValue value = dec.value == null
                ? new NullValue()
                : evaluate(dec.value,env);
        return env.declareVar(dec.name, value,dec.isFinal);
    }

    private static RuntimeValue evalBinaryExpr(Stmt astNode, Environment env) throws DeliccException {
        BinaryExpression expr = ((BinaryExpression) astNode);
        RuntimeValue lhs = evaluate(expr.left,env).toNumeric();
        RuntimeValue rhs = evaluate(expr.right,env).toNumeric();
        if((lhs instanceof StringValue || lhs instanceof NumberValue) &&
                (rhs instanceof StringValue || rhs instanceof NumberValue))
            return evalNumericBinaryExpression(lhs,rhs,expr.operator);
        return new NullValue();
    }

    private static RuntimeValue evalNumericBinaryExpression(RuntimeValue lhs, RuntimeValue rhs, String operator) {
        Object result = 0;
        switch (operator) {
            case "+":
                result = ((NumberValue) lhs).value + ((NumberValue) rhs).value;
                break;
            case "-":
                result = ((NumberValue) lhs).value - ((NumberValue) rhs).value;
                break;
            case "*":
                if(lhs instanceof StringValue && rhs instanceof NumberValue) {
                    result = new String(new char[Math.abs((int) ((NumberValue) rhs).value)]).replace("\0", ((StringValue) lhs).value);
                    break;
                }
                if (lhs instanceof NumberValue && rhs instanceof NumberValue) {
                    result = ((NumberValue) lhs).value * ((NumberValue) rhs).value;
                    break;
                }
                break;
            case "/":
                result = ((NumberValue) lhs).value / ((NumberValue) rhs).value;
                break;
            case "%":
                result = ((NumberValue) lhs).value % ((NumberValue) rhs).value;
                break;
            case "**":
                result = Math.pow(((NumberValue) lhs).value,((NumberValue) rhs).value);
                break;
            case "//":
                result = Math.floor(((NumberValue) lhs).value / ((NumberValue) rhs).value);
                break;
            default:
                result = 0;
                break;
        }
        if(result instanceof Double) {
            return new NumberValue(((Double) result));
        }
        return new StringValue((String) result);
    }

    private static RuntimeValue evaluateIdentifier(Stmt astNode, Environment env) throws DeliccException {
        Identifier ident = (Identifier) astNode;
        return env.lookup(ident.symbol);
    }

}
