package rookie.midl.codegen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * the collector of variable's declarator
 * @author Xuanchen Chen
 * @version 1.0
 * */
public class VarCollector implements Iterable<String> {

    /**
     * the map of qualified name -> declarator
     * we need to output the declarators in order of declaration, so we use an extra list to save the order
     * */
    private HashMap<String, List<Variable>> decls = new HashMap<>();

    private List<String> list = new ArrayList<>();

    private String curType;

    /**
     * create a new structure declaration
     * @param typename the name of this type
     * */
    public void createStruct(String typename) {
        this.decls.put(typename, new ArrayList<>());
        this.list.add(typename);
        this.curType = typename;
    }

    /**
     * add a new variable declarator to current struct
     * @param var information of variable
     * */
    public void addVar(Variable var) {
        if (curType == null) {
            throw new RuntimeException("you have not entered any structure's scope");
        }
        this.decls.get(curType).add(var);
    }

    /**
     * exit the scope of current structure
     * */
    public void exitAndSave() {
        curType = null;
    }

    public boolean isNested(String name) {
        List<Variable> variables = decls.get(name);
        return variables.stream().anyMatch(variable -> !variable.isPrimitive());
    }

    public List<Variable> getVariables(String name) {
        return decls.get(name);
    }

    @Override
    public Iterator<String> iterator() {
        return new VarIterator();
    }

    private class VarIterator implements Iterator<String> {

        private int i = 0;

        @Override
        public boolean hasNext() {
            return list.size() != i;
        }

        @Override
        public String next() {
            return list.get(i++);
        }
    }
}
