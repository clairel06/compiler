package scanner;
import java.io.*;

/**
 * Scanner is a simple scanner for Compilers and Interpreters (2014-2015). It 
 * supports two constructors and the methods getNextChar, eat, hasNext, isDigit,
 * isLetter, isTerminator, isOperator, isWhiteSpace, scanNumber, scanIdentifier,
 * scanOperator, checkDouble, and nextToken.
 * lab exercise 1
 * @author Claire Luo
 * @version September 7th, 2021
 *  
 * Usage:
 * <Insert a comment that shows how to use this object>
 *
 */
public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private char buffer;
    private char prevChar;
    private boolean eof;
    private boolean flag;
    /**
     * Scanner constructor for construction of a scanner that 
     * uses an InputStream object for input.  
     * Usage: 
     * FileInputStream inStream = new FileInputStream(new File(<file name>);
     * Scanner lex = new Scanner(inStream);
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        flag = false;
        getNextChar();
    }

    /**
     * Scanner constructor for constructing a scanner that 
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field currentChar.
     * Usage: Scanner lex = new Scanner(input_string);
     * @param inString the string to scan
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }

    /**
     * The getNextchar method utilizes the read method to take in the next 
     * character. If the character obtained is -1, the flag for the end of file 
     * is turned to true; otherwise, currentChar is set to the new value.
     * @postcondition currentChar has been updated and the end of file is also
     * updated if needed.
     */
    private void getNextChar()
    {
        try
        {
            if (flag) 
            {
                flag = false;
                return;
            }
            int tmp = in.read();
            prevChar = currentChar;
            if (tmp == -1) eof = true;
            else currentChar = (char) tmp;
        }
        catch (IOException e)
        {
            System.exit(-1);
        }
    }

    /**
     * the eat method takes in the next currentChar using the getNextChar method
     * @param expected is the supposed value of the next character
     * @throws ScanErrorException if expected is different from the next character.
     * @postcondition currentChar has been updated
     */

    private void eat(char expected) throws ScanErrorException
    {
        if (currentChar == expected) getNextChar();
        else throw new ScanErrorException("Illegal character - expected " + 
                expected + " and found " + currentChar);
    }

    /**
     * hasNext checks if there is another character in the string using the eof
     * variable.
     * @return true if there is another character, otherwise false
     */
    public boolean hasNext()
    {
        return !eof;
    }

    /**
     * checks if the current character is a digit
     * @param x is the character being checked for whether it is a digit or not
     * @return true if x is a digit, otherwise false
     */
    public static boolean isDigit(char x)
    {
        if (x >= '0' && x <= '9') return true;
        return false;
    }

    /**
     * checks if the current character is a letter
     * @param x is the character being checked for whether it is a letter or not
     * @return true if x is a letter, otherwise false
     */
    public static boolean isLetter(char x)
    {
        if ((x >= 'a' && x <= 'z') || (x >= 'A' && x <= 'Z')) return true;
        return false;
    }

    /**
     * checks if the current character is a whitespace
     * @param x is the character being checked for whether it is a whitespace or not
     * @return true if x is a whitespace, otherwise false
     */
    public static boolean isWhiteSpace(char x)
    {
        if (x == ' ' || x == '\t' || x == '\r' || x == '\n') return true;
        return false;
    }

    /**
     * the isOperator method checks if the character is an operator
     * @param x is the character being checked
     * @return true if x is an operator, otherwise false
     */
    public static boolean isOperator(char x)
    {
        if (x == '=' || x == '+' || x == '-' || x == '*' || x == '^' || x == '@'
            || x == '/' || x == '%' || x == '(' || x == ')' || x == ':'
            || x == '<' || x == '>' || x == '.' || x == ';' || x == ',') 
        {
            return true;
        }
        return false;
    }

    /**
     * the isTerminator method checks if the character is a terminator
     * @param x is the character being checked
     * @return true if x is a terminator, otherwise false
     */
    public static boolean isTerminator(char x)
    {
        if (isWhiteSpace(x)) return true;
        if (isOperator(x)) return true;
        //if (x == ';') return true;
        return false;
    }

    /**
     * scanNumber scans the input and returns the entire lexeme after terminated
     * @return the number in the form of a string
     * @throws ScanErrorException if there are non-digit characters in the number
     * @precondition the current character is a digit
     * @postcondition a new number token has been formed
     */
    private String scanNumber() throws ScanErrorException
    {
        String s = "";
        while (hasNext() && isDigit(currentChar))
        {
            s += currentChar;
            eat(currentChar);
        }
        if (!isTerminator(currentChar)) throw new ScanErrorException();
        //eat(currentChar);
        return s;
    }

    /**
     * scanIdentifier scans the input and returns the entire lexeme after terminated
     * @return the identifier in the form of a string
     * @throws ScanErrorException if there are non-digit or letter characters
     * @precondition the current character is an identifier
     * @postcondition a new identifier token has been formed
     */
    private String scanIdentifier() throws ScanErrorException
    {
        String s = "";
        while (hasNext() && (isDigit(currentChar) || isLetter(currentChar)))
        {
            s += currentChar;
            eat(currentChar);
        }
        if (!isTerminator(currentChar)) throw new ScanErrorException();
        //eat(currentChar);
        return s;
    }

    /**
     * checkDouble handles the cases for <=, >=, and := using a flag. It is 
     * called by and returns to the scanOperator method.
     * @precondition the previous character was either >, <, or :
     * @return 0 if the current character is '=', otherwise the current character
     * @throws ScanErrorException if currentChar doesn't match
     */
    private char checkDouble() throws ScanErrorException
    {
        char curChar = currentChar;
        eat(currentChar);
        if (curChar == '<') 
        {
            if (currentChar != '=' && currentChar != '>')
            {
                return currentChar;
            }
        }
        else if (currentChar != '=')
        {
            return curChar;
        }
        return 0;
    }
    
    /**
     * scanOperand scans the input and returns the entire lexeme after terminated
     * @return the operand in the form of a string
     * @throws ScanErrorException if the character isn't an operator
     * @precondition the current character is an operator
     * @postcondition a new operator token has been formed
     */
    private String scanOperator () throws ScanErrorException
    {
        String s = "";
        if (!isOperator(currentChar)) throw new ScanErrorException();
        s += currentChar;
        if (s.compareTo(".") == 0)
        {
            eat(currentChar);
            eof = true;
        }
        if (s.compareTo(">") == 0 || s.compareTo("<") == 0 || s.compareTo(":") == 0)
        {
            char check = checkDouble();
            if (check != 0)
            {
                flag = true;
                return s;
            }
            else 
            {
                s += currentChar;
            }
        }
        eat(currentChar);
        return s;
    }

    /**
     * Method: nextToken
     * @return the next token, whether it is an identifier, a number, or an operand.
     * @throws ScanErrorException if the character is not a proper character
     */
    public String nextToken() throws ScanErrorException
    {
        while (!eof) 
        {
            if (isWhiteSpace(currentChar)) eat(currentChar);
            else if (isLetter(currentChar)) return scanIdentifier();
            else if (isDigit(currentChar)) return scanNumber();
            else if (currentChar == '/') 
            {
                eat(currentChar);
                if (currentChar != '/') 
                {
                    return "/";
                } 
                else
                {
                    while (hasNext() && currentChar != '\n')
                    {
                        eat(currentChar);
                    }
                }
            }
            else if (isOperator(currentChar))
            {
                return scanOperator();
            }
            else if (isTerminator(currentChar)) 
            {
                eat(currentChar);
            }
            else 
            {
                throw new ScanErrorException();
            }
        }
        return "END";
    }    
}
