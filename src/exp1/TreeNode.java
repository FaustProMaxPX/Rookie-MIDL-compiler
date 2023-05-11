package exp1;

import java.util.ArrayList;
import java.util.List;

/*
 * AST所需元素：
 * 1. 节点类型
 * 2. 节点值
 * 3. 子节点
 * 4. 父节点
 * 5. 层级
 * 6. 兄弟节点
 * */
public class TreeNode {

    NodeKind nodeKind;

    NodeType nodeType;

    String val;

    List<TreeNode> children;

    TreeNode parent;

    int level;

    List<TreeNode> siblings;

    public TreeNode(NodeKind nodeKind, NodeType nodeType) {
        this(nodeKind, nodeType, null);
    }

    public TreeNode(NodeKind nodeKind, NodeType nodeType, String val) {
        this(nodeKind, nodeType, val, 0);
    }

    public TreeNode(NodeKind nodeKind, NodeType nodeType, String val, int level) {
        this.nodeType = nodeType;
        this.nodeKind = nodeKind;
        this.val = val;
        this.level = level;
        this.parent = null;
        this.siblings = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public void addChild(TreeNode child) {
        this.children.add(child);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(nodeType);
        if (this.nodeKind == NodeKind.TERMINAL) {
            builder.append(":").append(val);
        }
        builder.append("\n");
        for (TreeNode child : children) {
            builder.append('\t').append(child);
        }
        return builder.toString();
    }

    public String display() {
        return display(0);
    }

    public String display(int level) {
        StringBuilder builder = new StringBuilder();
        builder.append(nodeType);
        if (this.nodeKind == NodeKind.TERMINAL) {
            builder.append(":").append(val);
        }
        builder.append("\n");
        for (TreeNode child : children) {
            for (int i = 0; i <= level; i++) {
                builder.append('\t');
            }
            builder.append(child.display(level + 1));
        }
        return builder.toString();
    }

    enum NodeKind {
        TERMINAL,
        NON_TERMINAL,
    }

    enum NodeType {
        SPECIFICATION,
        DEFINITION,
        MODULE,
        TYPE_DECL,
        STRUCT_TYPE,
        MEMBER_LIST,
        TYPE_SPEC,
        SCOPED_NAME,
        BASE_TYPE_SPEC,
        FLOATING_PT_TYPE,
        INTEGER_TYPE,
        SIGNED_INT,
        UNSIGNED_INT,
        DECLARATORS,
        DECLARATOR,
        SIMPLE_DECLARATOR,
        ARRAY_DECLARATOR,
        EXP_LIST,
        OR_EXPR,
        XOR_EXPR,
        AND_EXPR,
        SHIFT_EXPR,
        ADD_EXPR,
        MULT_EXPR,
        UNARY_EXPR,
        LITERAL,
        ID,
        INTEGER,
        FLOATING_PT,
        CHAR,
        STRING,
        BOOLEAN,
        CONST,
    }
}
