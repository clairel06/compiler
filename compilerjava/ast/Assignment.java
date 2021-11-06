package ast;
import environment.*;

/**
 * The Assignment class supports the constructor, which takes in an expression and a variable,
 * as well as the execute method. It is a subclass of Statement.
 *
 * @author Claire Luo
 * @version October 21, 2021
 */
public class Assignment extends Statement
{
    private Expression exp;
    private String var;
    
    /**
     * Creates a new Assignment
     * @param exp is the expression stored of type Expression
     * @param var is the variable name stored of type String
     */
    public Assignment(Expression exp, String var)
    {
        this.exp = exp;
        this.var = var;
    }
    
    /**
     * Executes the Assignment, directly modifying the environment to set the variable to a 
     * new value
     * @param env is the edited environment
     */
    public void exec(Environment env)
    {
        env.setVariable(var,exp.eval(env));
    }
}
