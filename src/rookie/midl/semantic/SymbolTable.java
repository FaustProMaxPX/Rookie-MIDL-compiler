package rookie.midl.semantic;

import java.util.*;

/**
 * The structure to resolve analysis of semantic
 * composition:
 * a list of scope (the scope is presented by stack)
 * element in scope: simple name
 * a list of set consisting of id in corresponding scope
 *
 * @author Xuanchen Chen
 * @version 1.0
 * @date 2023.5.16
 * */
public class SymbolTable {

    private final List<Deque<String>> scopeList;

    private final List<Set<String>> nameSetList;

    private final Set<String> definedStructSet;

    public SymbolTable() {
        scopeList = new ArrayList<>();
        nameSetList = new ArrayList<>();
        definedStructSet = new HashSet<>();
//        initialize root scope
        scopeList.add(new ArrayDeque<>());
        nameSetList.add(new HashSet<>());
    }

    private Queue<String> getCurrentScope() {
        assert scopeList.size() > 0;
        return scopeList.get(scopeList.size() - 1);
    }

    private Set<String> getCurrentNameSet() {
        assert nameSetList.size() > 0;
        return nameSetList.get(nameSetList.size() - 1);
    }

    /**
     * add a new scope to table
     * */
    public void pushNewScope() {
        scopeList.add(new ArrayDeque<>());
        nameSetList.add(new HashSet<>());
    }

    /**
     * add an element to the newest scope without check
     * this method should be invoked after {@link #exist}
     * @param elem the element to be added
     * */
    public void pushElem(String elem) {
//        String qualifiedName = getQualifiedName(elem);
        Queue<String> currentScope = getCurrentScope();
        Set<String> currentNameSet = getCurrentNameSet();
        String name = getInnerDecl(elem);
        if (!currentNameSet.add(name)) {
            throw new RuntimeException("push element failed, because element with same name exists");
        }
        currentScope.add(name);
    }

    /**
     * checks if the element is in the current scope
     * @param elem the element to be checked
     * @return weather element exist
     * */
    public boolean exist(String elem) {
//        String qualifiedName = getQualifiedName(elem);
        Set<String> currentNameSet = getCurrentNameSet();
        String name = getInnerDecl(elem);
        return currentNameSet.contains(name);
    }

    /**
     * the combination of {@link #exist} and {@link #pushElem}
     * @param elem the element to be added
     * @return if the element has been added successfully
     * */
    public boolean checkAndPush(String elem) {
        if (exist(elem)) return false;
        pushElem(elem);
        return true;
    }

    /**
     * get the best inner name of a qualified name
     * eg: A::B -> B
     * @param elem a qualified name or a simple name
     * */
    private String getInnerDecl(String elem) {
        int index = elem.lastIndexOf("::");
        return elem.substring(index == -1 ? 0 : index + 2);
    }

    /**
     * get the qualified name of the specific elem.
     * if got an incomplete name, this method will complete it.
     * this method won't produce a right answer if you input a wrong parameter,
     * please make sure the correctness of the parameter by yourself
     * @param elem the best inner name of the element
     * */
    public String getQualifiedName(String elem) {
//        if elem use qualified name, don't process it
        if (elem.contains("::")) return elem;

        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < scopeList.size() - 1; i++) {
            buffer.append(scopeList.get(i).getLast()).append("::");
        }
        return buffer.append(elem).toString();
    }

    /**
     * remove the last scope in symbol table
     * */
    public void removeLastScope() {
        scopeList.remove(scopeList.size() - 1);
        nameSetList.remove(nameSetList.size() - 1);
    }

    /**
     * push the qualified name of the elem to relative set
     * @param elem the element defined in current scope
     * */
    public void pushDefinedStruct(String elem) {
        String qualifiedName = getQualifiedName(elem);
        definedStructSet.add(qualifiedName);
    }

    /**
     * check if the element is defined before
     * @param structName the name of a structure
     * */
    public boolean checkIfDefined(String structName) {
        String qualifiedName = getQualifiedName(structName);
        return definedStructSet.contains(qualifiedName);
    }
}
