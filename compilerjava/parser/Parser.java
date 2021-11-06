package parser;
import scanner.Scanner;
import scanner.ScanErrorException;
import ast.*;
import java.util.*;

/**
 * The Parser Class utilizes the Scanner class in order to parse statements token by token. 
 * It uses the constructor, eat, parseNumber, parseStatement, parseFactor, parseTerm, and 
 * parseExpression methods to do so.
 * 
 *
 * @author Claire Luo
 * @version September 30, 2021
 */
public class Parser
{
    private Scanner scanner;
    private String currentToken;
    Map<String, Integer> varmap = new HashMap<String, Integer>();
    /**
     * Creates a new parser
     * @param s is a Scanner which tokenizes the input
     * @throws ScanErrorException if Scanner's exception is thrown
     */
    public Parser(Scanner s) throws ScanErrorException
    {
        scanner = s;
        currentToken = scanner.nextToken();
    }

    /**
     * Checks if currentToken is the expected value and advances currentToken
     * @param expected is the token that is expected and compared to the real value of currentToken
     * @postcondition currentToken has been advanced
     * @throws ScanErrorException if expected does not match currentToken
     */
    private void eat(String expected) throws ScanErrorException
    {
        if (!expected.equals(currentToken))
        {
            throw new IllegalArgumentException("Expected:"+expected+" Received:" + currentToken);
        }
        currentToken = scanner.nextToken();
    }

    /**
     * Parses the condition, creating and returning a new Condition object
     * @return the new Condition object created
     * @precondition the previous token was "If" and the following tokens follow the correct
     * syntax
     */
    public Condition parseCondition() throws ScanErrorException
    {
        Expression exp1 = parseExpression();
        String relop = currentToken;
        eat(currentToken);
        Expression exp2 = parseExpression();
        return new Condition(exp1, relop, exp2);
    }

    /**
     * Parses the program. It will continue parsing each procedure and creating a new
     * ProcedureDeclaration object while there is another procedure. After that, it will parse 
     * the Statement which represents the "main" method.
     * @return a LinkedList of Programs which can be evaluated Program by Program, with each
     * Program representing either a ProcedureDeclaration or a statement.
     */
    public LinkedList<Program> parseProgram() throws ScanErrorException
    {
        LinkedList<Program> prgs = new LinkedList<Program>();
        while (currentToken.compareTo("PROCEDURE") == 0) 
        {
            eat("PROCEDURE");
            String name = currentToken;
            eat(currentToken);
            eat("(");
            LinkedList<String> params = new LinkedList<String>();
            while (currentToken.compareTo(")") != 0)
            {
                params.add(currentToken);
                eat(currentToken);
                if (currentToken.compareTo(")") != 0)
                {
                    eat(",");
                }
            }
            eat(")");
            eat(";");
            Statement stmt = parseStatement();
            prgs.add(new ProcedureDeclaration(name,stmt,params));
        }
        prgs.add(parseStatement());
        eat(".");
        return prgs;
    }

    /**
     * Handles the whole statement and the different constructions like Blocks, Writeln 
     * statements, conditionals, and while loops. To do this, it utilizes the parseCondition,
     * parseExpression, and parseStatement methods.
     * @postcondition currentToken has advanced to the end of the text.
     * @return the abstract syntax tree rooted with the statement
     * @throws ScanErrorException
     */
    public Statement parseStatement() throws ScanErrorException
    {
        if (currentToken.compareTo("BEGIN") == 0)
        {
            eat(currentToken);
            Block block = new Block();
            while (currentToken.compareTo("END") != 0) 
            {
                block.add(parseStatement());
            }
            eat("END");
            eat(";");
            return block;
        }
        else if (currentToken.compareTo("WRITELN") == 0)
        {
            eat(currentToken);
            eat("(");
            Expression exp = parseExpression();
            eat(")");
            eat(";");
            return new Writeln(exp);
        }
        else if (currentToken.compareTo("IF") == 0)
        {
            eat(currentToken);
            Condition cond = parseCondition();
            eat("THEN");
            Statement stmt = parseStatement();
            return new If(cond,stmt);
        }
        else if (currentToken.compareTo("WHILE") == 0)
        {
            eat(currentToken);
            Condition cond = parseCondition();
            eat("DO");
            Statement stmt = parseStatement();
            return new While(cond,stmt);
        }
        else 
        {
            String tmp = currentToken;
            eat(currentToken);
            eat(":=");
            Expression exp = parseExpression();
            eat(";");
            return new Assignment(exp, tmp);
        }
    }

    /**
     * Utilizes the parseInt method in order to change a string to an integer and returns a 
     * new Number object
     * @return a new Number object from the AST package
     * @precondition currentToken is an integer in string form
     * @postcondition currentToken has advanced
     * @throws ScanErrorException if currentToken is not a numerical value
     */
    private ast.Number parseNumber() throws ScanErrorException
    {
        int num = Integer.parseInt(currentToken);
        eat(currentToken);
        return new ast.Number(num);
    }

    /**
     * Parses the Factor, which can be either an expression enclosed in parentheses, the 
     * negative of a factor, a number, a procedureCall, or a variable. If it is a procedureCall,
     * the method will also parse in all of the arguments and create the procedureCall object
     * with all the new arguments.
     * @return a new object one of the following types: BinOp, Expression, 
     * procedureCall, or Variable.
     * @throws ScanErrorException if none of the if statements are satisfied, meaning that
     * the text does not follow the proper syntax.
     */
    public Expression parseFactor() throws ScanErrorException
    {
        if (currentToken.compareTo("-") == 0)
        {
            eat(currentToken);
            if (currentToken.compareTo("(") == 0)
            {
                eat("(");
                Expression exp=parseExpression();
                eat(")");
                return new BinOp("-", new ast.Number(0), exp);
            }
            ast.Number num = parseNumber();
            return new ast.Number(-num.getValue());
        }
        else if (currentToken.compareTo("(") == 0)
        {
            eat("(");
            Expression exp = parseExpression();
            eat(")");
            return exp;
        }
        else
        {
            if (currentToken.charAt(0) >= '0' && currentToken.charAt(0) <= '9')
            {
                return parseNumber();
            }
            else
            {
                String tmp = currentToken;
                eat(currentToken);
                if (currentToken.compareTo("(") != 0)
                {
                    return new Variable(tmp);
                }
                else
                {
                    LinkedList<Expression> args = new LinkedList<Expression>();
                    eat("(");
                    while (currentToken.compareTo(")") != 0)
                    {
                        Expression exp = parseExpression();
                        args.add(exp);
                        //eat(currentToken);
                        if (currentToken.compareTo(")") != 0)
                        {
                            eat(",");
                        }
                    }
                    eat(")");
                    return new ProcedureCall(tmp,args);
                }
            }
        }
    }

    /**
     * The parseTerm method can be either an expression and a factor separated by a "*" or
     * "/", or simply be another factor. If it is the latter case, it will return the 
     * Expression which can be obtained using the parseFactor method. Otherwise, a BinOp
     * object is returned, which includes the binary operator and the factor and expression
     * used in the expression.
     * @return an Expression object of one of the following classes: BinOp, Expression. 
     *  @throws ScanErrorException if the operands are not either * or /   
     */
    private Expression parseTerm() throws ScanErrorException
    {
        Expression tmp = parseFactor();
        while (true)
        {
            if (currentToken.compareTo("*") == 0)
            {
                eat("*");
                Expression factor = parseFactor();
                return new BinOp("*",tmp,factor); 
            }
            else if (currentToken.compareTo("/") == 0)
            {
                eat("/");
                Expression factor = parseFactor();
                return new BinOp("/",tmp,factor);
            }
            else
            {
                return tmp;
            }
        }
    }

    /**
     * the parseExpression method utilizes the parseTerm method in order to parse the 
     * Expression. It can be composed of an expression, "+", then a term, an expression, 
     * "-", then a term, or simply a single term. It then returns the newly created 
     * Expression object.
     * @return an Expression object that is either an Expression or a BinOp object, both of
     * which can then be evaluated for their numerical value.
     * @throws ScanErrorException if the operands that are part of each term aren't + or - 
     */
    private Expression parseExpression() throws ScanErrorException
    {
        Expression tmp = parseTerm();
        while (true)
        {
            if (currentToken.compareTo("+") == 0)
            {
                eat("+");
                Expression factor = parseTerm();
                return new BinOp("+",tmp,factor);
            }
            else if (currentToken.compareTo("-") == 0)
            {
                eat("-");
                Expression factor = parseTerm();
                return new BinOp("-",tmp,factor);           
            }
            else
            {
                return tmp;
            }
        }
    }
}

