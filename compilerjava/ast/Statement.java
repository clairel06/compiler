package ast;
import environment.*;

/**
 * The Statement class supports the abstract method exec, which is implemented by its subclasses
 * Writeln, Block, Assignment, If, and While. 
 *
 * @author Claire Luo
 * @version October 21, 2021
 */
public abstract class Statement extends Program
{
    /**
     * Calls upon the exec method of its subclasses to execute the statement
     * @param env is the environment that will be edited and store the variables
     */
    //public abstract void exec(Environment env);
}
