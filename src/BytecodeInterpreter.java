import java.util.ArrayList;

public class BytecodeInterpreter {
    private final ArrayList<Integer> dataMembers = new ArrayList<>();
    private final ArrayList<Integer> memoryArray = new ArrayList<>();
    private int accumulator = 0;
    private int memSize;

    public final int LOAD_OP = 0;
    public final int LOADI_OP = 1;
    public final int STORE_OP = 2;


    public void generate(int op, int operand) {
        dataMembers.add(op);
        dataMembers.add(operand);
    }


    public ArrayList<Integer> getDataMembers() {
        return dataMembers;
    }

    public ArrayList<Integer> getMemoryArray() {
        return memoryArray;
    }


    public ArrayList<Integer> getDataMembers() {
        return dataMembers;
    }

    public ArrayList<Integer> getMemoryArray() {
        return memoryArray;
    }

    public int getAccumulator() {
        return accumulator;
    }

    public int getMemSize() {
        return memSize;
    }

    private void load(int operand) throws Exception {
        accumulator += getMemoryArray().get(operand);
    }

    private void loadi(int operand) {
        accumulator += operand;
    }

    private void store(int operand) throws Exception {
        putInMemory(operand, accumulator);
        accumulator = 0;
    }

    private void putInMemory(int address, int val) throws Exception {
        memoryArray.set(address, val);
    }

    public BytecodeInterpreter(int memSize) {
        this.memSize = memSize;
        for (int i = 0; i < memSize; i++) {
            memoryArray.add(0);
        }
    }

    public void run() {
        for (int i = 0; i < dataMembers.size() / 2; i++) {

            int opcode = dataMembers.get(2 * i);
            int operand = dataMembers.get(2 * i + 1);
            try {
                if (opcode == 0)
                    load(operand);
                else if (opcode == 1)
                    loadi(operand);
                else
                    store(operand);
            } catch (Exception e) {
                System.out.println();
                log(true, "Error: Address out of range");
                return;
            }
        }

    }

    public void log(boolean error, String errorStr) {
        if (error)
            System.out.println(errorStr);
        System.out.print("ByteCode:[");
        System.out.print(dataMembers.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ")));
        System.out.println("]");
        System.out.print("Memory:[");
        System.out.print(memoryArray.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ")));
        System.out.println("]");
    }


    public static void main(String[] args) {
        BytecodeInterpreter interpreter = new BytecodeInterpreter(10);
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
