package rookie.midl.ast;

import java.util.ArrayList;
import java.util.List;
/*
 * AST所需元素：
 * 1. 节点类型
 * 2. 节点值类型
 * 3. 节点值
 * 4. 子节点
 * 5. 兄弟节点
 * */
public class TreeNode {

    NodeKind nodeKind;

    NodeType nodeType;

    String val;

    List<TreeNode> children;

    // 兄弟节点基本只在顶层用到
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
        this.siblings = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public void addChild(TreeNode child) {
        this.children.add(child);
    }

    public void addSib(TreeNode sib) {
        this.siblings.add(sib);
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
            builder.append("\t".repeat(Math.max(0, level + 1)));
            builder.append(child.display(level + 1));
        }
//        只有根节点才会到达这个循环
        for (TreeNode sibling : this.siblings) {
            builder.append("\n");
            builder.append(sibling.display(0));
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
        SHIFT_OP,
        ADD_OP,
        MULT_OP,
        UNARY_OP,
    }
}
