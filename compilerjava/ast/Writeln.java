package ast;
import environment.*;

/**
 * The Writeln class is the only way that anything can be printed. The expression to be printed
 * is stored in a variable. It is a subclass of Statement and supports the constructor and 
 * exec methods.
 *
 * @author Claire Luo
 * @version October 21, 2021
 */
public class Writeln extends Statement
{
    private Expression exp;
    /**
     * Creates a new Writeln object
     * @param exp is the expression whose value should be printed
     */
    public Writeln(Expression exp)
    {
        this.exp = exp;
    }
    
    /**
     * Executes the expression and then prints it.
     * @param env contains the values of the variables in the program and is passed
     * to the expression so it can evaluate it
     * @postcondition the value of the expression has been printed
     */
    public void exec(Environment env)
    {
        System.out.println(exp.eval(env));
    }
}
