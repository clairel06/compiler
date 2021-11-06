package ast;
import environment.*;

/**
 * Each Binop object is made of an operator and two expressions. It extends Expression and 
 * therefore implements the eval method, along with its constructor. 
 *
 * @author Claire Luo
 * @version October 21, 2021
 */
public class BinOp extends Expression
{
    private String op;
    private Expression exp1;
    private Expression exp2;

    /**
     * Creates a new BinOp object
     * @param op is the operator used in the operation
     * @param exp1 is the first expression
     * @param exp2 is the second expression
     */
    public BinOp(String op, Expression exp1, Expression exp2)
    {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    /**
     * Implements Expression's abstract eval method, testing for the different operators "+",
     * "-", "*", and "/" and calculating the value accordingly.
     * @param env stores the variables to pass on to the evaluate methods of expressions
     * @return the integer value of the expression
     */
    public int eval(Environment env)
    {
        if (op.compareTo("+") == 0)
        {
            return exp1.eval(env) + exp2.eval(env);
        }
        else if (op.compareTo("-") == 0)
        {
            return exp1.eval(env) - exp2.eval(env);
        }
        else if (op.compareTo("*") == 0)
        {
            return exp1.eval(env) * exp2.eval(env);
        }
        else
        {
            return exp1.eval(env) / exp2.eval(env);
        }
    }
}
