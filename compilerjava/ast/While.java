package ast;
import environment.*;

/**
 * The While Class represents a "Do..While.." construction in a piece of code. It can be
 * expressed using a condition and a statement. It is a subclass of Statement and supports
 * the methods exec and the constructor.
 *
 * @author Claire Luo
 * @version October 21, 2021
 */
public class While extends Statement
{
    Condition cond;
    Statement stmt;
    
    /**
     * Creates a new While object
     * @param cond is the condition that will be evaluated
     * @param stmt is the statement that will execute if the condition is true
     */
    public While(Condition cond, Statement stmt)
    {
        this.cond = cond;
        this.stmt = stmt;
    }
    
    /**
     * Executes the While construction. As long as the condition evaluates to true (using
     * the method condition.eval,) the statement will continue executing.
     * @param env is where the variables are stored and also what is passed to other
     * eval and exec methods.
     */
    public void exec(Environment env)
    {
        while (cond.eval(env)) 
        {
            stmt.exec(env);
        }
    }
}
