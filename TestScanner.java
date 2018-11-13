/**
 * A class for testing the HTMLScanner class
 *
 * @author Tia Newhall, Lisa Meeden (2000)
 * @author Benjamin Kuperman (Spring 2011)
 */ 

import java.io.*;

public class TestScanner {

    public static void main (String[] args) {

	if(args.length<1){
	    System.out.println("Usage: java TestScanner <url>");
	    System.exit(1);
	}

        try {
            String URL=args[0];
            HTMLScanner scanner = new HTMLScanner(URL);
            int count = 0;
            while(scanner.hasNext()) {
                String token = scanner.next();
                System.out.println("the "+count+"th token is #" + token + "#");
                count++;	
            }

        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }


    }

}
