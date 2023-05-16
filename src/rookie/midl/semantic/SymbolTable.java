package rookie.midl.semantic;

import java.util.*;

/**
 * The structure to resolve analysis of semantic
 * composition:
 * a list of scope (the scope is presented by stack)
 * element in scope: fully qualified name
 * a list of set consisting of id in corresponding scope
 *
 * @author Xuanchen Chen
 * @version 1.0
 * @date 2023.5.16
 * */
public class SymbolTable {

    private final List<Deque<String>> scopeList;

    private final List<Set<String>> nameSetList;

    public SymbolTable() {
        scopeList = new ArrayList<>();
        nameSetList = new ArrayList<>();
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
        String qualifiedName = getQualifiedName(elem);
        Queue<String> currentScope = getCurrentScope();
        Set<String> currentNameSet = getCurrentNameSet();
        if (!currentNameSet.add(qualifiedName)) {
            throw new RuntimeException("push element failed, because element with same name exists");
        }
        currentScope.add(qualifiedName);
    }

    /**
     * checks if the element is in the current scope
     * @param elem the element to be checked
     * @return weather element exist
     * */
    public boolean exist(String elem) {
        String qualifiedName = getQualifiedName(elem);
        Set<String> currentNameSet = getCurrentNameSet();
        return currentNameSet.contains(qualifiedName);
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
     * get the qualified name of the specific elem
     * it should only be used in current scope's element, this method won't check if you invoke it correctly
     * @param elem the best inner name of the element
     * */
    public String getQualifiedName(String elem) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < scopeList.size() - 2; i++) {
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
}
