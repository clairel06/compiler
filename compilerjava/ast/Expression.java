package ast;
import environment.*;

/**
 * The Expression class supports the eval method, which is implemented by its subclasses Number,
 * Binop, and Variable. 
 *
 * @author Claire Luo
 * @version October 21, 2021
 */
public abstract class Expression
{
    /**
     * Calls upon its subclasses' eval methods in order to evaluate the expression
     * @param env stores all the variables
     * @return the integer value of the expression
     */
    public abstract int eval(Environment env);
}
