// Generated from C:/Users/asus/IdeaProjects/Project 1/src/project1\Rules.g4 by ANTLR 4.7.2
package project1.antlr4;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link RulesParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface RulesVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link RulesParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(RulesParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuery(RulesParser.QueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#relationName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationName(RulesParser.RelationNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#attributeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeName(RulesParser.AttributeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(RulesParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand(RulesParser.CommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#atomicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomicExpr(RulesParser.AtomicExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#selection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelection(RulesParser.SelectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#projection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProjection(RulesParser.ProjectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#renaming}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRenaming(RulesParser.RenamingContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#union}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnion(RulesParser.UnionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#difference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDifference(RulesParser.DifferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#product}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProduct(RulesParser.ProductContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#naturalJoin}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNaturalJoin(RulesParser.NaturalJoinContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(RulesParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#conjunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConjunction(RulesParser.ConjunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#comparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison(RulesParser.ComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#operand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperand(RulesParser.OperandContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#attributeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeList(RulesParser.AttributeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#openCmd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpenCmd(RulesParser.OpenCmdContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#closeCmd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCloseCmd(RulesParser.CloseCmdContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#writeCmd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWriteCmd(RulesParser.WriteCmdContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#exitCmd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExitCmd(RulesParser.ExitCmdContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#showCmd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShowCmd(RulesParser.ShowCmdContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#createCmd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateCmd(RulesParser.CreateCmdContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#updateCmd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUpdateCmd(RulesParser.UpdateCmdContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#insertCmd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertCmd(RulesParser.InsertCmdContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#deleteCmd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeleteCmd(RulesParser.DeleteCmdContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#typedAttributeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypedAttributeList(RulesParser.TypedAttributeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(RulesParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link RulesParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(RulesParser.LiteralContext ctx);
}