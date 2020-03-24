import java.util.ArrayList;
import java.util.HashMap;

/**
 * SymTab maps the identifiers in the program to where they are stored in memory
 * It just wraps a HashMap to do its work.
 */
public class SymTab {
    HashMap<String,Integer> map = new HashMap<String,Integer>();

    /**
     *
     * @param id  is the identifier for which we want the address
     * @return
     */
    int getAddress(String id){
        if (map.get(id) == null) {
            return -1;  // return -1 if id not in symtab
        } else {
            return map.get(id).intValue();
        }
    }

    /**
     * add adds a new id to map if its not already in. It sets the address
     * of the id to map.size, i.e., the next available address 0, 1,...
     * @param id the identifier to add
     */
    void add(String id) {
        if (!map.containsKey(id)) {
            map.put(id, map.size());
        }
    }

    @Override
    public String toString() {
        return "SymTab:"+map;
    }
}
