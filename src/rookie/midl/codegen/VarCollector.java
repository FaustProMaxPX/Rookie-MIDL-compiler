package rookie.midl.codegen;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * the collector of variable's declarator
 * @author Xuanchen Chen
 * @version 1.0
 * */
public class VarCollector {

    /**
     * the map of qualified name -> declarator
     * we need to output the declarators in order of declaration, so we use treemap
     * */
    private TreeMap<String, List<Variable>> decls = new TreeMap<>();

    private String curType;

    /**
     * create a new structure declaration
     * @param typename the name of this type
     * */
    public void createStruct(String typename) {
        this.decls.put(typename, new ArrayList<>());
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
}
