package ast;
import environment.*;

/**
 * The Variable class represents a variable in a piece of code. It extends the Expression class
 * and supports the constructor and eval methods. The variable name is stored in a string.
 *
 * @author Claire Luo
 * @version October 21, 2021
 */
public class Variable extends Expression
{
    String name;

    /**
     * Creates a new Variable object
     * @param name is the name of the variable
     */
    public Variable(String name)
    {
        this.name = name;
    }
    
    /**
     * Evaluates the variable by returning the value of it stored in the environment
     * @param env is where the values of all variables are accessed from
     * @return the value stored in the variable
     */
    public int eval(Environment env)
    {
        return env.getVariable(name);
    }
}
