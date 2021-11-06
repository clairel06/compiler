package ast;
import environment.*;

/**
 * The Program Class is the head of the entire program. It can be made of as many procedures
 * as one desires, then followed by a statement describing what would typically be the "main"
 * class. It has subclasses of ProcedureDeclaration and Statement
 *
 * @author Claire Luo
 * @version October 25, 2021
 */
public abstract class Program
{
    /**
     * Executes the program based off of the type of program that it is: either a statement
     * or a procedure declaration. This is implemented by ProcedureDeclaration and all the 
     * subclasses of Statement, which include While, If, WriteLn, Block, and Assignment.
     * @param env is the top 'layer' of the environment, in other words, the global environment
     */
    public abstract void exec(Environment env);
}
