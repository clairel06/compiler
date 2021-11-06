package ast;
import java.util.*;
import environment.*;

/**
 * The Block object is made up of a list of statements. It extends the Statement class and
 * therefore implements the exec method, along with the constructor and the add methods.
 *
 * @author Claire Luo
 * @version October 21, 2021
 */
public class Block extends Statement
{
    private List<Statement> stmts;

    /**
     * Creates a new Block object
     */
    public Block()
    {
        stmts = new ArrayList<Statement>();
    }
    
    /**
     * Adds another statement to the stmts List
     * @param stmt is the statement added
     * @postcondition the stmts list has one extra statement
     */
    public void add(Statement stmt)
    {
        stmts.add(stmt);
    }
    
    /**
     * Implements Statement's exec method and executes each statement included in the 
     * stmts list.
     * @param env is used to store and pass along variables
     */
    public void exec(Environment env)
    {
        for (Statement s : stmts)
        {
            s.exec(env);
        }
    }
}
