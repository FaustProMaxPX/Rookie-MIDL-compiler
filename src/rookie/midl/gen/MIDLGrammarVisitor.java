package rookie.midl.gen;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MIDLGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MIDLGrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#specification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpecification(MIDLGrammarParser.SpecificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefinition(MIDLGrammarParser.DefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#module}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModule(MIDLGrammarParser.ModuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#type_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_decl(MIDLGrammarParser.Type_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#struct_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStruct_type(MIDLGrammarParser.Struct_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#member_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMember_list(MIDLGrammarParser.Member_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#type_spec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_spec(MIDLGrammarParser.Type_specContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#scoped_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScoped_name(MIDLGrammarParser.Scoped_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#base_type_spec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBase_type_spec(MIDLGrammarParser.Base_type_specContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#floating_pt_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloating_pt_type(MIDLGrammarParser.Floating_pt_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#integer_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteger_type(MIDLGrammarParser.Integer_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#signed_int}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSigned_int(MIDLGrammarParser.Signed_intContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#unsigned_int}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnsigned_int(MIDLGrammarParser.Unsigned_intContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#declarators}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarators(MIDLGrammarParser.DeclaratorsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#declarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarator(MIDLGrammarParser.DeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#simple_declarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimple_declarator(MIDLGrammarParser.Simple_declaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#array_declarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_declarator(MIDLGrammarParser.Array_declaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#exp_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp_list(MIDLGrammarParser.Exp_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#or_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOr_expr(MIDLGrammarParser.Or_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#xor_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXor_expr(MIDLGrammarParser.Xor_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#and_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd_expr(MIDLGrammarParser.And_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#shift_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShift_expr(MIDLGrammarParser.Shift_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#add_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdd_expr(MIDLGrammarParser.Add_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#mult_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMult_expr(MIDLGrammarParser.Mult_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#unary_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary_expr(MIDLGrammarParser.Unary_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MIDLGrammarParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(MIDLGrammarParser.LiteralContext ctx);
}