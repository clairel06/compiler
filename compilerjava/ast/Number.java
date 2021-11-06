package ast;
import environment.*;

/**
 * The Number class represents an integer and extends the Expression class. It supports the 
 * constructor, getValue, and eval methods.
 *
 * @author Claire Luo
 * @version October 21, 2021
 */
public class Number extends Expression
{
    private int value;

    /**
     * Creates a new Number object
     * @param value is the stored value of the number
     */
    public Number(int value)
    {
        this.value = value;
    }
    
    /**
     * returns the value stored in the Number
     * @return an integer describing the value stored
     */
    public int getValue()
    {
        return value;
    }

    /**
     * Implements the Expression abstract method eval by simply returning the numerical value
     * of the Number
     * @param env stores the variables within the program
     * @return the value of the Number
     */
    public int eval(Environment env)
    {
        return value;
    }
}
