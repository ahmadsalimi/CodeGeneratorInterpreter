
import java.util.ArrayList;

/**
 * Parser loops through a list of tokens (generated by lexer) and
 * checks to see if the tokens make a valid assignment statement program
 */
public class Parser {

    ArrayList<Token> tokens;
    SymTab symTab = new SymTab();
    private BytecodeInterpreter interpreter = new BytecodeInterpreter(10);

    int index;

    public Parser(String fileName) {
        Lexer lexer = new Lexer(fileName);
        System.out.println(lexer.buffer);
        tokens = lexer.getAllTokens();
    }

    /**
     * @return the token at the current index
     */
    Token nextToken() {
        Token t = tokens.get(index);
        index++;
        return t;
    }

    /**
     * when we've looked at a token to end an expression,
     * but want to put it back so parseAssignment can check it
     */
    void putTokenBack() {
        index--;
    }

    /**
     * parse an entire program
     *
     * @return true if valid program
     */
    public boolean parseProgram() {
        while (true) {
            if (parseAssignment()) {
                Token t = nextToken();
                if (t.getType() == Lexer.EOFTOKEN) {
                    return true;
                }
                putTokenBack();  // we jumped ahead
            } else {
                return false;
            }

        }
    }

    /**
     * parse a single assignment statement
     *
     * @return true if a valid assignment
     */
    public boolean parseAssignment() {
        Token idToken;
        idToken = parseIdentifier();
        if (idToken != null) {
            if (parseAssignOp()) {
                if (parseExpression()) {
                    interpreter.generate(interpreter.STORE_OP, symTab.getAddress(idToken.getValue()));
                    return true;
                }
                return false;
            } else {
                printError("Expecting assignment operator");
                return false;
            }
        } else {
            printError("Expecting identifier");
            return false;
        }
    }

    /**
     * parse the left-hand side of an assignment, e.g., the "x" in "x=5+y"
     *
     * @return true if the current token is an id
     */
    public Token parseIdentifier() {
        Token t = nextToken();
        if (t.getType().equals(Lexer.IDTOKEN)) {
            symTab.add(t.getValue());
            return t;
        } else {
            return null;
        }
    }

    /**
     * parse the =
     *
     * @return true if current token is an =
     */
    public boolean parseAssignOp() {
        Token t = nextToken();
        return (t.getType().equals(Lexer.ASSMTTOKEN));
    }

    /**
     * parse the right hand side of an expression, the "y+5" in "x=y+5"
     *
     * @return true if the next tokens are a valid expression
     */
    public boolean parseExpression() {
        Token t = nextToken();
        if ((t.getType() == Lexer.INTTOKEN) || (t.getType() == Lexer.IDTOKEN)) {
            while (true) {
                if (t.getType() == Lexer.IDTOKEN) {
                    // better be in symtab already
                    if (symTab.getAddress(t.getValue()) == -1) {
                        printError("variable undefined");
                        return false;
                    }
                    interpreter.generate(interpreter.LOAD_OP, symTab.getAddress(t.getValue()));
                }

                if (t.getType() == Lexer.INTTOKEN) {
                    interpreter.generate(interpreter.LOADI_OP, Integer.parseInt(t.getValue()));
                }

                t = nextToken();
                if (t.getType() == Lexer.PLUSTOKEN) {
                    t = nextToken();

                    if (t.getType() != Lexer.INTTOKEN && t.getType() != Lexer.IDTOKEN) {
                        printError("expecting id or int");
                        return false;
                    }

                } else {
                    putTokenBack();
                    return true;
                }
            }
        } else {
            printError("expecting id or int");
            return false;
        }

    }

    /**
     * printError adds the current lineNum to an error to be printed
     */
    public void printError(String error) {
        putTokenBack();
        Token curToken = this.tokens.get(index);
        System.out.println("Error: " + error + " at line " + curToken.getLineNum());
    }

    public String toString() {
        return this.tokens.toString() + "\n" + this.symTab.toString();
    }

    public static void main(String[] args) {
        Parser parser = new Parser("resources/testWhiteSpace.txt");
        for (Token t : parser.tokens) {
            System.out.println(t);
        }
        if (parser.parseProgram()) {
            System.out.println("Valid Program");
            System.out.println(parser.symTab);

            for (int data : parser.interpreter.getDataMembers()) {
                System.out.print(data + " ");
            }
            System.out.println();

            for (String variable : parser.symTab.map.keySet()) {
                System.out.println(variable + " = " + parser.interpreter.getMemoryArray().get(parser.symTab.map.get(variable)));
            }

            System.out.println(parser.interpreter);
        } else {
            System.out.println("invalid Program");
        }

    }


///*
//                        LOOP
// */
//
//        String[] fileArray = {"test.txt", "test5.txt", "textExpectingId2.txt",
//                "testExpectingAssignOp.txt", "testExpectingIdOrInt2.txt", "testMultiplePlus.txt", "testWhiteSpace.txt"
//                , "testNotDefined.txt", "testSecondLineError.txt", "idNums.txt"};
//
//
//        for (String filename : fileArray) {
//            System.out.println(filename);
//            Parser parser = new Parser(filename);
//            //System.out.println(parser.tokens);
//            for (Token t : parser.tokens) {
//                System.out.println(t);
//            }
//
//
//            if (parser.parseProgram()) {
//                System.out.println("Valid Program");
//                System.out.println(parser.symTab);
//
//            } else {
//                System.out.println("invalid Program");
//            }
//            System.out.println();
//
//        }
//    }

}

