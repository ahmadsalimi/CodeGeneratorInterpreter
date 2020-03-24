import java.util.ArrayList;

public class BytecodeInterpreter {
    private final ArrayList<Integer> dataMembers = new ArrayList<>();

    public void generate(int op, int operand) {
        dataMembers.add(op);
        dataMembers.add(operand);
    }

    public void run() {

    }

    public static void main(String[] args) {
        BytecodeInterpreter interpreter = new BytecodeInterpreter();
        interpreter.generate(0, 1);
        interpreter.generate(2, 5);
        interpreter.generate(3, 7);
        interpreter.generate(1, 10);

        for (int data : interpreter.dataMembers) {
            System.out.print(data + " ");
        }
        System.out.println();
    }
}
