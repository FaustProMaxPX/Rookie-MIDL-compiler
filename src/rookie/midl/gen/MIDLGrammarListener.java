package rookie.midl.gen;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MIDLGrammarParser}.
 */
public interface MIDLGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#specification}.
	 * @param ctx the parse tree
	 */
	void enterSpecification(MIDLGrammarParser.SpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#specification}.
	 * @param ctx the parse tree
	 */
	void exitSpecification(MIDLGrammarParser.SpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#definition}.
	 * @param ctx the parse tree
	 */
	void enterDefinition(MIDLGrammarParser.DefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#definition}.
	 * @param ctx the parse tree
	 */
	void exitDefinition(MIDLGrammarParser.DefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#module}.
	 * @param ctx the parse tree
	 */
	void enterModule(MIDLGrammarParser.ModuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#module}.
	 * @param ctx the parse tree
	 */
	void exitModule(MIDLGrammarParser.ModuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#type_decl}.
	 * @param ctx the parse tree
	 */
	void enterType_decl(MIDLGrammarParser.Type_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#type_decl}.
	 * @param ctx the parse tree
	 */
	void exitType_decl(MIDLGrammarParser.Type_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#struct_type}.
	 * @param ctx the parse tree
	 */
	void enterStruct_type(MIDLGrammarParser.Struct_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#struct_type}.
	 * @param ctx the parse tree
	 */
	void exitStruct_type(MIDLGrammarParser.Struct_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#member_list}.
	 * @param ctx the parse tree
	 */
	void enterMember_list(MIDLGrammarParser.Member_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#member_list}.
	 * @param ctx the parse tree
	 */
	void exitMember_list(MIDLGrammarParser.Member_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#type_spec}.
	 * @param ctx the parse tree
	 */
	void enterType_spec(MIDLGrammarParser.Type_specContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#type_spec}.
	 * @param ctx the parse tree
	 */
	void exitType_spec(MIDLGrammarParser.Type_specContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#scoped_name}.
	 * @param ctx the parse tree
	 */
	void enterScoped_name(MIDLGrammarParser.Scoped_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#scoped_name}.
	 * @param ctx the parse tree
	 */
	void exitScoped_name(MIDLGrammarParser.Scoped_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#base_type_spec}.
	 * @param ctx the parse tree
	 */
	void enterBase_type_spec(MIDLGrammarParser.Base_type_specContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#base_type_spec}.
	 * @param ctx the parse tree
	 */
	void exitBase_type_spec(MIDLGrammarParser.Base_type_specContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#floating_pt_type}.
	 * @param ctx the parse tree
	 */
	void enterFloating_pt_type(MIDLGrammarParser.Floating_pt_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#floating_pt_type}.
	 * @param ctx the parse tree
	 */
	void exitFloating_pt_type(MIDLGrammarParser.Floating_pt_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#integer_type}.
	 * @param ctx the parse tree
	 */
	void enterInteger_type(MIDLGrammarParser.Integer_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#integer_type}.
	 * @param ctx the parse tree
	 */
	void exitInteger_type(MIDLGrammarParser.Integer_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#signed_int}.
	 * @param ctx the parse tree
	 */
	void enterSigned_int(MIDLGrammarParser.Signed_intContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#signed_int}.
	 * @param ctx the parse tree
	 */
	void exitSigned_int(MIDLGrammarParser.Signed_intContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#unsigned_int}.
	 * @param ctx the parse tree
	 */
	void enterUnsigned_int(MIDLGrammarParser.Unsigned_intContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#unsigned_int}.
	 * @param ctx the parse tree
	 */
	void exitUnsigned_int(MIDLGrammarParser.Unsigned_intContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#declarators}.
	 * @param ctx the parse tree
	 */
	void enterDeclarators(MIDLGrammarParser.DeclaratorsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#declarators}.
	 * @param ctx the parse tree
	 */
	void exitDeclarators(MIDLGrammarParser.DeclaratorsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#declarator}.
	 * @param ctx the parse tree
	 */
	void enterDeclarator(MIDLGrammarParser.DeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#declarator}.
	 * @param ctx the parse tree
	 */
	void exitDeclarator(MIDLGrammarParser.DeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#simple_declarator}.
	 * @param ctx the parse tree
	 */
	void enterSimple_declarator(MIDLGrammarParser.Simple_declaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#simple_declarator}.
	 * @param ctx the parse tree
	 */
	void exitSimple_declarator(MIDLGrammarParser.Simple_declaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#array_declarator}.
	 * @param ctx the parse tree
	 */
	void enterArray_declarator(MIDLGrammarParser.Array_declaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#array_declarator}.
	 * @param ctx the parse tree
	 */
	void exitArray_declarator(MIDLGrammarParser.Array_declaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#exp_list}.
	 * @param ctx the parse tree
	 */
	void enterExp_list(MIDLGrammarParser.Exp_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#exp_list}.
	 * @param ctx the parse tree
	 */
	void exitExp_list(MIDLGrammarParser.Exp_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#or_expr}.
	 * @param ctx the parse tree
	 */
	void enterOr_expr(MIDLGrammarParser.Or_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#or_expr}.
	 * @param ctx the parse tree
	 */
	void exitOr_expr(MIDLGrammarParser.Or_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#xor_expr}.
	 * @param ctx the parse tree
	 */
	void enterXor_expr(MIDLGrammarParser.Xor_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#xor_expr}.
	 * @param ctx the parse tree
	 */
	void exitXor_expr(MIDLGrammarParser.Xor_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#and_expr}.
	 * @param ctx the parse tree
	 */
	void enterAnd_expr(MIDLGrammarParser.And_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#and_expr}.
	 * @param ctx the parse tree
	 */
	void exitAnd_expr(MIDLGrammarParser.And_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#shift_expr}.
	 * @param ctx the parse tree
	 */
	void enterShift_expr(MIDLGrammarParser.Shift_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#shift_expr}.
	 * @param ctx the parse tree
	 */
	void exitShift_expr(MIDLGrammarParser.Shift_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#add_expr}.
	 * @param ctx the parse tree
	 */
	void enterAdd_expr(MIDLGrammarParser.Add_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#add_expr}.
	 * @param ctx the parse tree
	 */
	void exitAdd_expr(MIDLGrammarParser.Add_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#mult_expr}.
	 * @param ctx the parse tree
	 */
	void enterMult_expr(MIDLGrammarParser.Mult_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#mult_expr}.
	 * @param ctx the parse tree
	 */
	void exitMult_expr(MIDLGrammarParser.Mult_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#unary_expr}.
	 * @param ctx the parse tree
	 */
	void enterUnary_expr(MIDLGrammarParser.Unary_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#unary_expr}.
	 * @param ctx the parse tree
	 */
	void exitUnary_expr(MIDLGrammarParser.Unary_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MIDLGrammarParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(MIDLGrammarParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MIDLGrammarParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(MIDLGrammarParser.LiteralContext ctx);
}