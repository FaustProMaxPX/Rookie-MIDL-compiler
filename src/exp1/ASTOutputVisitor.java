package exp1;

import gen.MIDLGrammarBaseVisitor;
import gen.MIDLGrammarParser;
import org.antlr.v4.runtime.tree.TerminalNode;

import static exp1.TreeNode.NodeKind.NON_TERMINAL;
import static exp1.TreeNode.NodeKind.TERMINAL;
import static exp1.TreeNode.NodeType.*;

public class ASTOutputVisitor extends MIDLGrammarBaseVisitor<TreeNode> {


    /**
     * specification -> definition { definition }
     * 该节点的AST对应于一条definition链表，链表长度至少为1
     * definition - definition - ...
     * */
    @Override
    public TreeNode visitSpecification(MIDLGrammarParser.SpecificationContext ctx) {
        TreeNode root = visitDefinition(ctx.definition(0));
        for (int i = 1; i < ctx.definition().size(); i++) {
            root.addSib(visitDefinition(ctx.definition(i)));
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
     * 包含ID和定义两种节点，定义节点有0..n个兄弟节点
     * 这里可以直接将所有的definition归于module的子节点下
     * */
    @Override
    public TreeNode visitModule(MIDLGrammarParser.ModuleContext ctx) {

        TreeNode root = new TreeNode(NON_TERMINAL, MODULE);
//        TreeNode child = new TreeNode(TERMINAL, CONST, "module");
        root.addChild(new TreeNode(TERMINAL, ID, ctx.ID().getText()));
        for (MIDLGrammarParser.DefinitionContext definitionContext : ctx.definition()) {
            root.addChild(visitDefinition(definitionContext));
        }
        return root;
    }

    /**
     * type_decl -> struct_type | “struct” ID
     * 该节点有两种可能，子节点为struct_type或ID
     * */
    @Override
    public TreeNode visitType_decl(MIDLGrammarParser.Type_declContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, TYPE_DECL);
        if (ctx.struct_type() != null) {
            root.addChild(visitStruct_type(ctx.struct_type()));
        } else {
//            TreeNode child = new TreeNode(TERMINAL, CONST, "struct");
            root.addChild(new TreeNode(TERMINAL, ID, ctx.ID().getText()));
//            root.addChild(child);
        }
        return root;
    }

    /**
     * struct_type->“struct” ID “{”   member_list “}”
     * 该节点有两个子节点,ID和member_list
     * */
    @Override
    public TreeNode visitStruct_type(MIDLGrammarParser.Struct_typeContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, STRUCT_TYPE);
        root.addChild(new TreeNode(TERMINAL, ID, ctx.ID().getText()));
        root.addChild(visitMember_list(ctx.member_list()));
        return root;
    }

    /**
     * member_list-> { type_spec declarators “;” }
     * 该节点的子节点由任意数量的type_spec和declarators组成
     * 每一对type_spec和declarators之间为兄弟关系,但这里可以直接全部作为member_list的子节点
     * */
    @Override
    public TreeNode visitMember_list(MIDLGrammarParser.Member_listContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, MEMBER_LIST);
        for (int i = 0; i < ctx.type_spec().size(); i++) {
            MIDLGrammarParser.Type_specContext specContext = ctx.type_spec(i);
            MIDLGrammarParser.DeclaratorsContext declaratorsContext = ctx.declarators(i);
            TreeNode child = visitType_spec(specContext);
            child.addChild(visitDeclarators(declaratorsContext));
            root.addChild(child);
        }
        return root;
    }

    /**
     * type_spec -> scoped_name | base_type_spec | struct_type
     * */
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

    /**
     * scoped_name -> [“::”] ID {“::” ID }
     * 由任意数量的ID组成,可以直接作为子节点集合串在当前节点下面
     * */
    @Override
    public TreeNode visitScoped_name(MIDLGrammarParser.Scoped_nameContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, SCOPED_NAME);
        for (TerminalNode terminalNode : ctx.ID()) {
            root.addChild(new TreeNode(TERMINAL, ID, terminalNode.getText()));
        }
        return root;
    }

    /**
     * base_type_spec->floating_pt_type|integer_type|“char”|“string”|“boolean”
     * */
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

    /**
     * declarators -> declarator {“,” declarator }
     * 处理方式同上
     * */
    @Override
    public TreeNode visitDeclarators(MIDLGrammarParser.DeclaratorsContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, DECLARATORS);
        for (MIDLGrammarParser.DeclaratorContext declaratorContext : ctx.declarator()) {
            root.addChild(visitDeclarator(declaratorContext));
        }
        return root;
    }

    /**
     * declarator -> simple_declarator | array_declarator
     * */
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

    /**
     * simple_declarator -> ID [“=” or_expr]
     * =可以直接忽略,将or_expr与ID连接
     * */
    @Override
    public TreeNode visitSimple_declarator(MIDLGrammarParser.Simple_declaratorContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, SIMPLE_DECLARATOR);
        root.addChild(new TreeNode(TERMINAL, ID, ctx.ID().getText()));
        if (ctx.or_expr() != null) {
            root.addChild(visitOr_expr(ctx.or_expr()));
        }
        return root;
    }

    /**
     * array_declarator -> ID “[” or_expr “]” [“=” exp_list ]
     * ID与or_expr必有,exp_list可选,其余部分忽略
     * */
    @Override
    public TreeNode visitArray_declarator(MIDLGrammarParser.Array_declaratorContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, ARRAY_DECLARATOR);
        root.addChild(new TreeNode(TERMINAL, ID, ctx.ID().getText()));
        root.addChild(visitOr_expr(ctx.or_expr()));
        if (ctx.exp_list() != null) {
            root.addChild(visitExp_list(ctx.exp_list()));
        }
        return root;
    }

    /**
     * exp_list -> “[” or_expr { “,”or_expr } “]”
     * */
    @Override
    public TreeNode visitExp_list(MIDLGrammarParser.Exp_listContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, EXP_LIST);
        for (MIDLGrammarParser.Or_exprContext orExprContext : ctx.or_expr()) {
            root.addChild(visitOr_expr(orExprContext));
        }
        return root;
    }

    /**
     * or_expr -> xor_expr {“|” xor_expr }
     * 由于这里只可能有一种运算符,因此直接忽略,下面同理
     * */
    @Override
    public TreeNode visitOr_expr(MIDLGrammarParser.Or_exprContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, OR_EXPR);
        root.addChild(visitXor_expr(ctx.xor_expr(0)));
        for (int i = 1; i < ctx.xor_expr().size(); i++) {
            root.addChild(visitXor_expr(ctx.xor_expr(i)));
        }
        return root;
    }

    /**
     * xor_expr -> and_expr {“^” and_expr }
     * */
    @Override
    public TreeNode visitXor_expr(MIDLGrammarParser.Xor_exprContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, XOR_EXPR);
        root.addChild(visitAnd_expr(ctx.and_expr(0)));
        for (int i = 1; i < ctx.and_expr().size(); i++) {
            root.addChild(visitAnd_expr(ctx.and_expr(i)));
        }
        return root;
    }

    /**
     * and_expr -> shift_expr {“&”shift_expr }
     * */
    @Override
    public TreeNode visitAnd_expr(MIDLGrammarParser.And_exprContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, AND_EXPR);
        root.addChild(visitShift_expr(ctx.shift_expr(0)));
        for (int i = 1; i < ctx.shift_expr().size(); i++) {
            root.addChild(visitShift_expr(ctx.shift_expr(i)));
        }
        return root;
    }

    /**
     * shift_expr -> add_expr { (“>>” | “<<”) add_expr }
     * 这里的运算符有两种可能,需要生成一个节点来存储
     * */
    @Override
    public TreeNode visitShift_expr(MIDLGrammarParser.Shift_exprContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, SHIFT_EXPR);
        root.addChild(visitAdd_expr(ctx.add_expr(0)));
        for (int i = 1; i < ctx.add_expr().size(); i++) {
            TreeNode child = new TreeNode(TERMINAL, SHIFT_OP, ctx.SHIFT_OP(i - 1).getText());
            child.addChild(visitAdd_expr(ctx.add_expr(i)));
            root.addChild(child);
        }
        return root;
    }

    /**
     * add_expr -> mult_expr { (“+” | “-”) mult_expr }
     * */
    @Override
    public TreeNode visitAdd_expr(MIDLGrammarParser.Add_exprContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, ADD_EXPR);
        root.addChild(visitMult_expr(ctx.mult_expr(0)));
        for (int i = 1; i < ctx.mult_expr().size(); i++) {
            TreeNode child = new TreeNode(TERMINAL, ADD_OP, ctx.ADD_OP(i - 1).getText());
            child.addChild(visitMult_expr(ctx.mult_expr(i)));
            root.addChild(child);
        }
        return root;
    }

    @Override
    public TreeNode visitMult_expr(MIDLGrammarParser.Mult_exprContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, MULT_EXPR);
        root.addChild(visitUnary_expr(ctx.unary_expr(0)));
        for (int i = 1; i < ctx.unary_expr().size(); i++) {
            TreeNode child = new TreeNode(TERMINAL, MULT_OP, ctx.MULT_OP(i - 1).getText());
            child.addChild(visitUnary_expr(ctx.unary_expr(i)));
            root.addChild(child);
        }
        return root;
    }

    @Override
    public TreeNode visitUnary_expr(MIDLGrammarParser.Unary_exprContext ctx) {
        TreeNode root = new TreeNode(NON_TERMINAL, UNARY_EXPR);
        if (ctx.UNARY_OP() != null)
            root.addChild(new TreeNode(TERMINAL, UNARY_OP, ctx.UNARY_OP().getText()));
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
