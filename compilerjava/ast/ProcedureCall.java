package ast;
import parser.*;
import java.util.*;
import environment.*;

/**
 * The ProcedureCall along with the ProcedureDeclaration classes allow each procedure
 * to be executed. A ProcedureCall object is made when the procedure is called upon within
 * the method. It has two instance variables, the name of the procedure, and the arguments
 * passed into the procedure.
 *
 * @author Claire Luo
 * @version October 25, 2021
 */
public class ProcedureCall extends Expression
{
    String name;
    LinkedList<Expression> args;

    /**
     * Creates a new ProcedureCall object
     * @param name is the name of the procedure
     * @param args are the values passed into the procedure call
     */
    public ProcedureCall(String name, LinkedList<Expression> args)
    {
        this.name = name;
        this.args = args;
    }

    /**
     * Evaluates a procedure call and returns the value of the call
     * @param env stores all the variables in the current scope
     * @return the value of the procedure when using the arguments provided
     */
    public int eval(Environment env)
    {
        return env.callProcedure(name,args);
    }
}
