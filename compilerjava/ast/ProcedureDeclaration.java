package ast;
import java.util.*;
import environment.*;

/**
 * A ProcedureDeclaration object contains the name of the procedure, the body of the procedure,
 * as well as the list of expected parameters that should be given when the procedure is called.
 *
 * @author Claire Luo
 * @version October 25, 2021
 */
public class ProcedureDeclaration extends Program
{
    private String name;
    private Statement stmt;
    LinkedList<String> params;

    /**
     * Creates a new ProcedureDeclaration object with parameters
     * @param name is the name of the procedure
     * @param stmt is the body of the procedure
     * @param params is the list of expected parameters whenever the procedure is called
     */
    public ProcedureDeclaration(String name, Statement stmt, LinkedList<String> params)
    {
        this.name = name;
        this.stmt = stmt;
        this.params = params;
    }

    /**
     * Implements the abstract exec method defined in Program. Because this is a 
     * procedureDeclaration rather than a procedureCall, it only adds the procedure to the 
     * procedure map in the provided environment.
     * @param env is the environment to which the procedure is added to
     */
    public void exec(Environment env)
    {
        env.addProcedure(name, this);
    }

    /**
     * Takes the params list instance variables and turns it into an array for easier traversal.
     * This is used by the Environment callProcedure method so that when the variable list
     * is traversed, this array can be run through with a simple index.
     * @return the array form of the list of parameters
     */
    public String[] getParams()
    {
        String[] a = new String[params.size()];
        int ind = 0;
        for (String s : params)
        {
            a[ind] = s;
            ind++;
        }
        return a;
    }

    /**
     * Returns the body or Statement portion of the procedure
     * @return the body or statement portion of the procedure for use in execution
     */
    public Statement getBody()
    {
        return stmt;
    }
}
