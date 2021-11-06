package ast;
import environment.*;

/**
 * The If class, which is an extension of the Statement class, supports the constructor and 
 * exec methods. An 'If' object is composed of a condition and a statement, and evaluates 
 * the statement of condition 
 *
 * @author Claire Luo
 * @version October 21, 2021
 */
public class If extends Statement
{
    Condition cond;
    Statement stmt;
    
    /**
     * Creates a new If object
     * @param cond is the condition portion of the If Object
     * @param stmt is the statement portion of the If object
     */
    public If(Condition cond, Statement stmt)
    {
        this.cond = cond;
        this.stmt = stmt;
    }
    
    /**
     * Executes the If objects, executing the statement if the condition is true.
     * @param env is the Environment which stores the variables
     */
    public void exec(Environment env)
    {
        if (cond.eval(env)) 
        {
            stmt.exec(env);
        }
    }
    
}
