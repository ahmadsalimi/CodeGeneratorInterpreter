import java.util.ArrayList;

public class BytecodeInterpreter {
    public final int LOAD_OP = 0;
    public final int LOADI_OP = 1;
    public final int STORE_OP = 2;
    private final ArrayList<Integer> dataMembers = new ArrayList<>();
    private final ArrayList<Integer> memoryArray = new ArrayList<>();
    private int accumulator = 0;
    private int memSize = 0;

    public BytecodeInterpreter(int memSize) {
        this.memSize = memSize;
        for (int i = 0; i < memSize; i++) {
            memoryArray.add(0);
        }
    }

    private void load(int operand) {

    }

    private void loadi(int operand) {

    }

    private void store(int operand) {

    }

    public void generate(int op, int operand) {
        dataMembers.add(op);
        dataMembers.add(operand);
    }

    public void run() {

    }

    public static void main(String[] args) {
        BytecodeInterpreter interpreter = new BytecodeInterpreter(3);
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
