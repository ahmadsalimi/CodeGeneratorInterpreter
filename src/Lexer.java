
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class to build an array of Tokens from an input file
 * @author wolberd
 * @see Token
 * @see Parser
 * @see
 */
public class Lexer {

    String buffer;
    int index = 0;
    int lineNum=1;
    public static final String INTTOKEN="INT";
    public static final String IDTOKEN="ID";
    public static final String ASSMTTOKEN="ASSMT";
    public static final String PLUSTOKEN="PLUS";
    public static final String EOFTOKEN="EOF";
    public static final String UNKOWTOKEN="UNKNOW";

    /**
     * call getInput to get the file data into our buffer
     * @param fileName the file we'll open
     */
    public Lexer(String fileName) {
        getInput(fileName);
    }

    /**
     * Reads given file into the data member buffer
     *
     * @param fileName name of file to parse
     */
    private void getInput(String fileName)  {
        try {
            Path filePath = Paths.get(fileName);
            byte[] allBytes = Files.readAllBytes(filePath);
            buffer = new String (allBytes);
        } catch (IOException e) {
            System.out.println ("You did not enter a valid file name in the run arguments.");
            System.out.println ("Please enter a string to be parsed:");
            Scanner scanner = new Scanner(System.in);
            buffer=scanner.nextLine();
        }
    }

    /**
     * looks at the buffer to see which type of token, then gets the rest of token
     * @return the next token in the input buffer
     */
    public Token getNextToken() {
        Token t = null;
        // first get past whitespace
        while ((index<buffer.length()) && Character.isWhitespace(buffer.charAt(index))) {
            char c = buffer.charAt(index);
            if (buffer.charAt(index)=='\n'){
                lineNum++;
            }
            index++;
        }
        if (index < buffer.length()) {
            char ch = buffer.charAt(index);
            if (ch == '=') {
                index++;
                t = new Token(ASSMTTOKEN, "=", lineNum);
                return t;
            } else if (ch=='+') {
                index++;
                t = new Token(PLUSTOKEN, "+", lineNum);
                return t;
            } else if ((ch <= 'z' && ch >= 'a') || (ch <= 'Z' && ch >= 'A')) { // is an id
                t = getIdentifier();
                return t;
            } else if( ch<='9' && ch>='0'){
                    t = getInteger();
                    return t;

            }else{
                index++;
                t = new Token(UNKOWTOKEN,Character.toString(ch),lineNum);
            }
        } else {
            t = new Token("EOF", "-", lineNum);

        }
        return t;

    }

    /**
     * if we get here, we've seen first letter in id. This func gets the rest
     * @return the token, its type will be ID
     */
    private Token getIdentifier() {
        boolean readingLetterDig = true;

        String value="";
        while (readingLetterDig) {
            // look at next char and see if it is valid for an id
            if (index>=buffer.length()) {
                readingLetterDig=false;
            }
            else {
                char ch = buffer.charAt(index);
                if ((ch <= 'z' && ch >= 'a') || (ch <= 'Z' && ch >= 'A') || (ch <= '9' && ch >= '0') ){ // is valid
                    value = value + ch;
                    index++;
                } else {
                    readingLetterDig = false;
                }
            }
        }
        Token t = new Token(IDTOKEN, value, lineNum);
        return t;
    }

    /**
     * if we get here, we've seen first digit in int This func gets the rest
     * @return the token, its type will be INT
     */
    private Token getInteger() {
        boolean readingDigit = true;

       String value="";
        while (readingDigit) {
            // look at next char and see if it is valid for an id
            if (index>=buffer.length()) {
                readingDigit=false;
            }
            else {
                char ch = buffer.charAt(index);
                if (ch <= '9' && ch >= '0') { // is valid
                    value = value + ch;
                    index++;
                } else {
                    readingDigit = false;
                }
            }
        }
        Token t = new Token(INTTOKEN, value, lineNum);
        return t;
    }

    /**
     * keep calling getNextToken until get to end
     * @return the arraylist of all tokens in file
     */
    public ArrayList<Token> getAllTokens() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        Token token = getNextToken();
        while (!token.getType().equals(EOFTOKEN)) {
            tokens.add(token);
            token = getNextToken();
        }
        tokens.add(token);   //EOF
        return tokens;
    }

    public static void main(String[] args) {
        String fileName="idNums.txt";
//        if (args.length==0) {
//            System.out.println("You must specify a file name");
//        } else {
//
//            fileName=args[0];
//        }
        Lexer lexer = new Lexer(fileName);

        System.out.println(lexer.buffer);
        ArrayList<Token> tokens = lexer.getAllTokens();

        for(Token t: tokens){
            System.out.println(t);
        }
    }
}
	