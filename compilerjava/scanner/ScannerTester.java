package scanner;
import java.util.*;
import java.io.*;
/**
 * ScannerTester uses files ScannerTest.txt and scannerTestAdvanced.txt
 * in order to check the Scanner class and its methods.
 * 
 * @author  Claire Luo 
 *
 * @version September 2nd, 2021 
 */
public class ScannerTester 
{
    /* ScannerTester methods: */

    /**
     * The main function creates a new scanner and prints out all the tokens 
     * in order to check that they are correct.
     *
     * @param args arguments from the command line
     */
    public static void main(String [ ] args) throws ScanErrorException, FileNotFoundException
    {
        FileInputStream inStream = new FileInputStream(new File("parserTest0.txt"));

        Scanner scanner = new Scanner(inStream);
        while (scanner.hasNext())

        {
            //System.out.println("hello");
            System.out.println(scanner.nextToken());
              //         System.out.println("end");

        }
    }

}
