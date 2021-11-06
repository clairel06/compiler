package environment;
import java.util.*;
import ast.*;

/**
 *  The environment classes uses a HashMap in order to store the variables created by
 *  Assignment objects. It uses another HashMap to store the name of procedures and 
 *  the corresponding procedureDeclaration. It supports the constructor and the methods 
 *  setVariable, getVariable, addProcedure, declareVariable, and callProcedure.
 *
 * @author Claire Luo
 * @version October 25, 2021
 */
public class Environment
{
    private HashMap<String,Integer> vars;
    private HashMap<String, ProcedureDeclaration> procedures;
    private Environment parent; 
    /**
     * Creates a new Environment object
     * @param par is the environment the current environment is hanging off of. If it is
     * the main environment, the par is set to null.
     * @postcondition a new Environment object has been created
     */
    public Environment(Environment par)
    {
        vars = new HashMap<String,Integer>();
        procedures = new HashMap<String,ProcedureDeclaration>();
        parent = par;
    }

    /**
     * Sets a variable in the HashMap to a new value if it exists inside the current environment
     * or the parent environment if it is ull. Otherwise, it will call setVariable on the parent 
     * environment.
     * @param variable is the variable whose valuable will be added or changed
     * @param value is the value the variable is being assigned to
     */
    public void setVariable(String variable, int value)
    {
        if (parent == null || vars.containsKey(variable))
        {
            vars.put(variable,value);
        }
        else
        {
            parent.setVariable(variable,value);
        }
    }

    /**
     * Adds another procedure to the procedures map.
     * @param name is the name of the procedure
     * @param dec is the procedureDeclation which stores the statement and name 
     */
    public void addProcedure(String name, ProcedureDeclaration dec)
    {
        procedures.put(name,dec);
    }

    /**
     * Retrieves the value stored in a specific variable and returns it
     * @param variable is where the value should be retrieved from
     * @return the integer value currently stored in the variable
     */
    public int getVariable(String variable)
    {
        if (vars.containsKey(variable))
        {
            return vars.get(variable);
        }
        return parent.getVariable(variable);
    } 

    /**
     * Executes the procedure with name "proc." It does this by first obtaining the procedure
     * declaration from the highest parent class and then getting all the parameters from
     * the procedure declaration. Next, it creates a new environment hanging off the current
     * environment and declares all the new variables inside that new environment. Lastly,
     * it obtains the statement and executes it.
     * @param proc is the name of the procedure to be called
     * @param args is the list of expressions representing the arguments passed to the function
     * @return the integer value of the procedure statement
     */
    public int callProcedure(String proc, LinkedList<Expression> args)
    {
        Environment par = this;
        while (par.parent != null)
        {
            par = par.parent;
        }
        ProcedureDeclaration dec = par.procedures.get(proc);
        
        String[] a = dec.getParams();
        int ind = 0;
        Environment newenv = new Environment(this);
        newenv.declareVariable(proc,0);
        for (Expression exp : args)
        {
            int val = exp.eval(this);
            newenv.declareVariable(a[ind], val);
            ind++;
        }
        Statement stmt = dec.getBody();
        stmt.exec(newenv);
        return newenv.getVariable(proc);
    }

    /**
     * Creates a new entry in the variable map within the current environment even if 
     * it exists in the parent environment.
     * @param variable is the variable name to be stored
     * @param value is the value to be stored in the variable name
     */
    public void declareVariable(String variable, int value)
    {
        vars.put(variable, value);
    }
}
