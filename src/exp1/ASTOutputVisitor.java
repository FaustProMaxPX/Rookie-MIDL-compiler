package exp1;
import gen.*;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;
import java.util.Objects;

import static exp1.TreeNode.NodeType.*;
import static exp1.TreeNode.NodeKind.*;

public class ASTOutputVisitor extends MIDLGrammarBaseVisitor<TreeNode> {


    /**
     * specification -> definition { definition }
     * 该节点的AST对应于一条definition链表，链表长度至少为1
     * definition - definition - ...
     * */
    @Override
    public TreeNode visitSpecification(MIDLGrammarParser.SpecificationContext ctx) {

        TreeNode root = new TreeNode(NON_TERMINAL, SPECIFICATION);
        for (MIDLGrammarParser.DefinitionContext definitionContext : ctx.definition()) {
            root.addChild(visitDefinition(definitionContext));
        }
        return root;
    }

    /**
     * definiton -> type_decl“;”| module “;”
     * 对应单个节点，type_decl | module
     * */
    @Override
    public TreeNode visitDefinition(MIDLGrammarParser.DefinitionContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, DEFINITION);
        if (ctx.type_decl() != null) {
            root.addChild(visitType_decl(ctx.type_decl()));
        } else {
            root.addChild(visitModule(ctx.module()));
        }
        return root;
    }

    /**
     * module -> “module”ID “{” definition { definition } “}”
     * 包含ID和定义两个节点，定义节点有0..n个兄弟节点
     * */
    @Override
    public TreeNode visitModule(MIDLGrammarParser.ModuleContext ctx) {

        TreeNode root = new TreeNode(NON_TERMINAL, MODULE);
        TreeNode child = new TreeNode(TERMINAL, CONST, "module");
        child.addChild(new TreeNode(TERMINAL, ID, ctx.ID().getText()));
        for (MIDLGrammarParser.DefinitionContext definitionContext : ctx.definition()) {
            child.addChild(visitDefinition(definitionContext));
        }
        root.addChild(child);
        return root;
    }

    @Override
    public TreeNode visitType_decl(MIDLGrammarParser.Type_declContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, TYPE_DECL);
        if (ctx.struct_type() != null) {
            root.addChild(visitStruct_type(ctx.struct_type()));
        } else {
            TreeNode child = new TreeNode(TERMINAL, CONST, "struct");
            child.addChild(new TreeNode(TERMINAL, ID, ctx.ID().getText()));
            root.addChild(child);
        }
        return root;
    }

    @Override
    public TreeNode visitStruct_type(MIDLGrammarParser.Struct_typeContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, STRUCT_TYPE);
        root.addChild(new TreeNode(TERMINAL, ID, ctx.ID().getText()));
        root.addChild(visitMember_list(ctx.member_list()));
        return root;
    }

    @Override
    public TreeNode visitMember_list(MIDLGrammarParser.Member_listContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, MEMBER_LIST);
        for (int i = 0; i < ctx.type_spec().size(); i++) {
            MIDLGrammarParser.Type_specContext specContext = ctx.type_spec().get(i);
            MIDLGrammarParser.DeclaratorsContext declaratorsContext = ctx.declarators().get(i);
            TreeNode child = visitType_spec(specContext);
            child.addChild(visitDeclarators(declaratorsContext));
            root.addChild(child);
        }
        return root;
    }

    @Override
    public TreeNode visitType_spec(MIDLGrammarParser.Type_specContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, TYPE_SPEC);
        if (ctx.scoped_name() != null) {
            root.addChild(visitScoped_name(ctx.scoped_name()));
        } else if (ctx.base_type_spec() != null) {
            root.addChild(visitBase_type_spec(ctx.base_type_spec()));
        } else if (ctx.struct_type() != null) {
            root.addChild(visitStruct_type(ctx.struct_type()));
        }
        return root;
    }

    @Override
    public TreeNode visitScoped_name(MIDLGrammarParser.Scoped_nameContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, SCOPED_NAME);
        for (TerminalNode terminalNode : ctx.ID()) {
            root.addChild(new TreeNode(TERMINAL, ID, terminalNode.getText()));
        }
        return root;
    }

    @Override
    public TreeNode visitBase_type_spec(MIDLGrammarParser.Base_type_specContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, BASE_TYPE_SPEC);
        if (ctx.floating_pt_type() != null) {
            root.addChild(visitFloating_pt_type(ctx.floating_pt_type()));
        } else if (ctx.integer_type() != null) {
            root.addChild(visitInteger_type(ctx.integer_type()));
        } else {
            root.addChild(new TreeNode(TERMINAL, CONST, ctx.getText()));
        }
        return root;
    }

    @Override
    public TreeNode visitFloating_pt_type(MIDLGrammarParser.Floating_pt_typeContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, FLOATING_PT_TYPE);
        root.addChild(new TreeNode(TERMINAL, CONST, ctx.getText()));
        return root;
    }

    @Override
    public TreeNode visitInteger_type(MIDLGrammarParser.Integer_typeContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, INTEGER_TYPE);
        if (ctx.signed_int() != null) {
            root.addChild(visitSigned_int(ctx.signed_int()));
        } else if (ctx.unsigned_int() != null) {
            root.addChild(visitUnsigned_int(ctx.unsigned_int()));
        }
        return root;
    }

    @Override
    public TreeNode visitSigned_int(MIDLGrammarParser.Signed_intContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, SIGNED_INT);
        root.addChild(new TreeNode(TERMINAL, CONST, ctx.getText()));
        return root;
    }

    @Override
    public TreeNode visitUnsigned_int(MIDLGrammarParser.Unsigned_intContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, UNSIGNED_INT);
        root.addChild(new TreeNode(TERMINAL, CONST, ctx.getText()));
        return root;
    }

    @Override
    public TreeNode visitDeclarators(MIDLGrammarParser.DeclaratorsContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, DECLARATORS);
        for (MIDLGrammarParser.DeclaratorContext declaratorContext : ctx.declarator()) {
            root.addChild(visitDeclarator(declaratorContext));
        }
        return root;
    }

    @Override
    public TreeNode visitDeclarator(MIDLGrammarParser.DeclaratorContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, DECLARATOR);
        if (ctx.array_declarator() != null) {
            root.addChild(visitArray_declarator(ctx.array_declarator()));
        } else if (ctx.simple_declarator() != null) {
            root.addChild(visitSimple_declarator(ctx.simple_declarator()));
        }
        return root;
    }

    @Override
    public TreeNode visitSimple_declarator(MIDLGrammarParser.Simple_declaratorContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, SIMPLE_DECLARATOR);
        TreeNode child = new TreeNode(TERMINAL, ID, ctx.ID().getText());
        if (ctx.or_expr() != null) {
            TreeNode eqNode = new TreeNode(TERMINAL, CONST, "=");
            eqNode.addChild(visitOr_expr(ctx.or_expr()));
            child.addChild(eqNode);
        }
        root.addChild(child);
        return root;
    }

    @Override
    public TreeNode visitArray_declarator(MIDLGrammarParser.Array_declaratorContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, ARRAY_DECLARATOR);
        root.addChild(new TreeNode(TERMINAL, ID, ctx.ID().getText()));
        if (ctx.exp_list() != null) {
            TreeNode child = new TreeNode(TERMINAL, CONST, "=");
            child.addChild(visitExp_list(ctx.exp_list()));
        }
        return root;
    }

    @Override
    public TreeNode visitExp_list(MIDLGrammarParser.Exp_listContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, EXP_LIST);
        for (MIDLGrammarParser.Or_exprContext orExprContext : ctx.or_expr()) {
            root.addChild(visitOr_expr(orExprContext));
        }
        return root;
    }

    @Override
    public TreeNode visitOr_expr(MIDLGrammarParser.Or_exprContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, OR_EXPR);
        List<MIDLGrammarParser.Xor_exprContext> contexts = ctx.xor_expr();
        root.addChild(visitXor_expr(contexts.get(0)));
        for (int i = 1; i < contexts.size(); i++) {
            TreeNode child = new TreeNode(TERMINAL, CONST, "|");
            child.addChild(visitXor_expr(contexts.get(i)));
            root.addChild(child);
        }
        return root;
    }

    @Override
    public TreeNode visitXor_expr(MIDLGrammarParser.Xor_exprContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, XOR_EXPR);
        List<MIDLGrammarParser.And_exprContext> contexts = ctx.and_expr();
        root.addChild(visitAnd_expr(contexts.get(0)));
        for (int i = 1; i < contexts.size(); i++) {
            TreeNode child = new TreeNode(TERMINAL, CONST, "^");
            child.addChild(visitAnd_expr(contexts.get(i)));
            root.addChild(child);
        }
        return root;
    }

    @Override
    public TreeNode visitAnd_expr(MIDLGrammarParser.And_exprContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, AND_EXPR);
        List<MIDLGrammarParser.Shift_exprContext> contexts = ctx.shift_expr();
        root.addChild(visitShift_expr(contexts.get(0)));
        for (int i = 1; i < contexts.size(); i++) {
            TreeNode child = new TreeNode(TERMINAL, CONST, "&");
            child.addChild(visitShift_expr(contexts.get(i)));
            root.addChild(child);
        }
        return root;
    }

    @Override
    public TreeNode visitShift_expr(MIDLGrammarParser.Shift_exprContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, SHIFT_EXPR);
        List<MIDLGrammarParser.Add_exprContext> contexts = ctx.add_expr();
        root.addChild(visitAdd_expr(contexts.get(0)));
        for (int i = 1; i < contexts.size(); i++) {
            TreeNode child = null;
            if (ctx.getText().contains(">>")) child = new TreeNode(TERMINAL, CONST, ">>");
            else if (ctx.getText().contains("<<")) child = new TreeNode(TERMINAL, CONST, "<<");
            assert child != null;
            child.addChild(visitAdd_expr(contexts.get(i)));
            root.addChild(child);
        }
        return root;
    }

    @Override
    public TreeNode visitAdd_expr(MIDLGrammarParser.Add_exprContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, ADD_EXPR);
        List<MIDLGrammarParser.Mult_exprContext> contexts = ctx.mult_expr();
        root.addChild(visitMult_expr(contexts.get(0)));
        String text = ctx.getText();
        for (int i = 1; i < contexts.size(); i++) {
            TreeNode child = null;
            if (text.contains("+")) child = new TreeNode(TERMINAL, CONST, "+");
            else if (text.contains("-")) child = new TreeNode(TERMINAL, CONST, "-");
            assert child != null;
            child.addChild(visitMult_expr(contexts.get(i)));
            root.addChild(child);
        }
        return root;
    }

    @Override
    public TreeNode visitMult_expr(MIDLGrammarParser.Mult_exprContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, MULT_EXPR);
        List<MIDLGrammarParser.Unary_exprContext> contexts = ctx.unary_expr();
        root.addChild(visitUnary_expr(contexts.get(0)));
        for (int i = 1; i < contexts.size(); i++) {
            TreeNode child = null;
            if (ctx.getText().contains("*")) child = new TreeNode(TERMINAL, CONST, "*");
            else if (ctx.getText().contains("/")) child = new TreeNode(TERMINAL, CONST, "/");
            else if (ctx.getText().contains("%")) child = new TreeNode(TERMINAL, CONST, "%");
            assert child != null;
            child.addChild(visitUnary_expr(contexts.get(i)));
            root.addChild(child);
        }
        return root;
    }

    @Override
    public TreeNode visitUnary_expr(MIDLGrammarParser.Unary_exprContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, UNARY_EXPR);
        if (ctx.getText().contains("-")) {
            root.addChild(new TreeNode(TERMINAL, CONST, "-"));
        } else if (ctx.getText().contains("+")) {
            root.addChild(new TreeNode(TERMINAL, CONST, "+"));
        } else if (ctx.getText().contains("~")) {
            root.addChild(new TreeNode(TERMINAL, CONST, "~"));
        }
        root.addChild(visitLiteral(ctx.literal()));
        return root;
    }

    @Override
    public TreeNode visitLiteral(MIDLGrammarParser.LiteralContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, LITERAL);
        if (ctx.INTEGER() != null) {
            root.addChild(new TreeNode(TERMINAL, INTEGER, ctx.INTEGER().getText()));
        } else if (ctx.FLOATING_PT() != null) {
            root.addChild(new TreeNode(TERMINAL, FLOATING_PT, ctx.FLOATING_PT().getText()));
        } else if (ctx.CHAR() != null) {
            root.addChild(new TreeNode(TERMINAL, CHAR, ctx.CHAR().getText()));
        } else if (ctx.BOOLEAN() != null) {
            root.addChild(new TreeNode(TERMINAL, BOOLEAN, ctx.BOOLEAN().getText()));
        } else if (ctx.STRING() != null) {
            root.addChild(new TreeNode(TERMINAL, STRING, ctx.STRING().getText()));
        }
        return root;
    }
}
