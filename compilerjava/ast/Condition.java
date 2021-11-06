package ast;
import environment.*;

/**
 * The Condition class is made up of a relative operator and two expressions. Being a subclass
 * or Statement, it implements the eval method along with the constructor.
 *
 * @author Claire Luo
 * @version October 21, 2021
 */
public class Condition
{
    Expression exp1;
    Expression exp2;
    String relop;
    
    /**
     * creates a new Condition object
     * @param exp1 is the first expression in the comparison
     * @param relop is the type of operator being used in the comparisons
     * @param exp2 is the second expression being evaluated
     */
    public Condition(Expression exp1, String relop, Expression exp2)
    {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.relop = relop;
    }

    /**
     * Computes whether the condition is true or false and returns the answer
     * @param env stores the variables so that they can be passed to evaluate the individual
     * expressions
     * @return whether the condition is true or not
     */
    public boolean eval(Environment env)
    {
        if (relop.compareTo("=") == 0) return exp1.eval(env) == exp2.eval(env);
        if (relop.compareTo("<>") == 0) return exp1.eval(env) != exp2.eval(env);
        if (relop.compareTo("<") == 0) return exp1.eval(env) < exp2.eval(env);
        if (relop.compareTo(">") == 0) return exp1.eval(env) > exp2.eval(env);
        if (relop.compareTo("<=") == 0) return exp1.eval(env) <= exp2.eval(env);
        if (relop.compareTo(">=") == 0) return exp1.eval(env) >= exp2.eval(env);
        return false;
    }
}
