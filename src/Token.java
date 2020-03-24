/**
 * class Token packages the type and value of each token in a string of text
 * and also keeps track of the line number where the token appeared.
 */
public class Token {
    private String value;
    private String type;
    private int lineNum;

    public Token(String t, String v, int lineNum) {
        this.value = v;
        this.type = t;
        this.lineNum = lineNum;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getLineNum() {
        return lineNum;
    }

    @Override
    public String toString() {
        return (String.format("%-10s%-8sline%s", type, value, lineNum));
    }
}