package rookie.midl.codegen;

/**
 * the structure for saving information of variables
 * composition:
 * 1. the type of variable
 * 2. the name of variable
 * 3. its expression
 * 4. if it is an array, if true, we also need to know its size
 * @author Xuanchen Chen
 * @version 1.0
 * */
public class Variable {
    private String type;
    private String name;
    private String expr;
    private boolean isArr;
    private int arrSize;

    public Variable() {
        this.arrSize = -1;
    }

    public Variable(String type, String name, String expr, boolean isArr, int arrSize) {
        this.type = type;
        this.name = name;
        this.expr = expr;
        this.isArr = isArr;
        this.arrSize = arrSize;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    public void setArr(boolean arr) {
        isArr = arr;
    }

    public void setArrSize(int arrSize) {
        this.arrSize = arrSize;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getExpr() {
        return expr;
    }

    public boolean isArr() {
        return isArr;
    }

    public int getArrSize() {
        return arrSize;
    }
}
