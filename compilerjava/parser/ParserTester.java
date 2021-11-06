package parser;
import java.util.*;
import java.io.*;
import scanner.Scanner;
import scanner.ScanErrorException;
import ast.*;
import environment.*;

/**
 * The ParserTester class first uses a Scanner to tokenize the user input, then uses the 
 * Parser object in order to create the abstract syntax tree for the statement. Then it 
 * evaluates the abstract syntax tree with a call to statement.exec.
 * @author Claire Luo
 * @version September 30, 2021
 */
public class ParserTester 
{
    /**
     * Creates a new ParserTester object
     * @param args arguments from the command line
     */
    public static void main(String [ ] args) throws ScanErrorException, FileNotFoundException
    {
        FileInputStream inStream = new FileInputStream(new File("parserTest7.txt"));
        Scanner scanner = new Scanner(inStream);
        Parser parser = new Parser(scanner);
        Environment env = new Environment(null);
        LinkedList<Program> prog = parser.parseProgram();
        for (Program p : prog)
        {
            p.exec(env);
        }
    }
}
