// Generated from C:/Users/kelvin/Documents/GitHub/CSCE315-Project-1/project1\Rules.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RulesParser}.
 */
public interface RulesListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RulesParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(RulesParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(RulesParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(RulesParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(RulesParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#relationName}.
	 * @param ctx the parse tree
	 */
	void enterRelationName(RulesParser.RelationNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#relationName}.
	 * @param ctx the parse tree
	 */
	void exitRelationName(RulesParser.RelationNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#attributeName}.
	 * @param ctx the parse tree
	 */
	void enterAttributeName(RulesParser.AttributeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#attributeName}.
	 * @param ctx the parse tree
	 */
	void exitAttributeName(RulesParser.AttributeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(RulesParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(RulesParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommand(RulesParser.CommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommand(RulesParser.CommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#atomicExpr}.
	 * @param ctx the parse tree
	 */
	void enterAtomicExpr(RulesParser.AtomicExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#atomicExpr}.
	 * @param ctx the parse tree
	 */
	void exitAtomicExpr(RulesParser.AtomicExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#selection}.
	 * @param ctx the parse tree
	 */
	void enterSelection(RulesParser.SelectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#selection}.
	 * @param ctx the parse tree
	 */
	void exitSelection(RulesParser.SelectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#projection}.
	 * @param ctx the parse tree
	 */
	void enterProjection(RulesParser.ProjectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#projection}.
	 * @param ctx the parse tree
	 */
	void exitProjection(RulesParser.ProjectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#renaming}.
	 * @param ctx the parse tree
	 */
	void enterRenaming(RulesParser.RenamingContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#renaming}.
	 * @param ctx the parse tree
	 */
	void exitRenaming(RulesParser.RenamingContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#union}.
	 * @param ctx the parse tree
	 */
	void enterUnion(RulesParser.UnionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#union}.
	 * @param ctx the parse tree
	 */
	void exitUnion(RulesParser.UnionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#difference}.
	 * @param ctx the parse tree
	 */
	void enterDifference(RulesParser.DifferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#difference}.
	 * @param ctx the parse tree
	 */
	void exitDifference(RulesParser.DifferenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#product}.
	 * @param ctx the parse tree
	 */
	void enterProduct(RulesParser.ProductContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#product}.
	 * @param ctx the parse tree
	 */
	void exitProduct(RulesParser.ProductContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#naturalJoin}.
	 * @param ctx the parse tree
	 */
	void enterNaturalJoin(RulesParser.NaturalJoinContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#naturalJoin}.
	 * @param ctx the parse tree
	 */
	void exitNaturalJoin(RulesParser.NaturalJoinContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(RulesParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(RulesParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#conjunction}.
	 * @param ctx the parse tree
	 */
	void enterConjunction(RulesParser.ConjunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#conjunction}.
	 * @param ctx the parse tree
	 */
	void exitConjunction(RulesParser.ConjunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparison(RulesParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparison(RulesParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#operand}.
	 * @param ctx the parse tree
	 */
	void enterOperand(RulesParser.OperandContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#operand}.
	 * @param ctx the parse tree
	 */
	void exitOperand(RulesParser.OperandContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#attributeList}.
	 * @param ctx the parse tree
	 */
	void enterAttributeList(RulesParser.AttributeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#attributeList}.
	 * @param ctx the parse tree
	 */
	void exitAttributeList(RulesParser.AttributeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#openCmd}.
	 * @param ctx the parse tree
	 */
	void enterOpenCmd(RulesParser.OpenCmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#openCmd}.
	 * @param ctx the parse tree
	 */
	void exitOpenCmd(RulesParser.OpenCmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#closeCmd}.
	 * @param ctx the parse tree
	 */
	void enterCloseCmd(RulesParser.CloseCmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#closeCmd}.
	 * @param ctx the parse tree
	 */
	void exitCloseCmd(RulesParser.CloseCmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#writeCmd}.
	 * @param ctx the parse tree
	 */
	void enterWriteCmd(RulesParser.WriteCmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#writeCmd}.
	 * @param ctx the parse tree
	 */
	void exitWriteCmd(RulesParser.WriteCmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#exitCmd}.
	 * @param ctx the parse tree
	 */
	void enterExitCmd(RulesParser.ExitCmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#exitCmd}.
	 * @param ctx the parse tree
	 */
	void exitExitCmd(RulesParser.ExitCmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#showCmd}.
	 * @param ctx the parse tree
	 */
	void enterShowCmd(RulesParser.ShowCmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#showCmd}.
	 * @param ctx the parse tree
	 */
	void exitShowCmd(RulesParser.ShowCmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#createCmd}.
	 * @param ctx the parse tree
	 */
	void enterCreateCmd(RulesParser.CreateCmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#createCmd}.
	 * @param ctx the parse tree
	 */
	void exitCreateCmd(RulesParser.CreateCmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#updateCmd}.
	 * @param ctx the parse tree
	 */
	void enterUpdateCmd(RulesParser.UpdateCmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#updateCmd}.
	 * @param ctx the parse tree
	 */
	void exitUpdateCmd(RulesParser.UpdateCmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#insertCmd}.
	 * @param ctx the parse tree
	 */
	void enterInsertCmd(RulesParser.InsertCmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#insertCmd}.
	 * @param ctx the parse tree
	 */
	void exitInsertCmd(RulesParser.InsertCmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#deleteCmd}.
	 * @param ctx the parse tree
	 */
	void enterDeleteCmd(RulesParser.DeleteCmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#deleteCmd}.
	 * @param ctx the parse tree
	 */
	void exitDeleteCmd(RulesParser.DeleteCmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#typedAttributeList}.
	 * @param ctx the parse tree
	 */
	void enterTypedAttributeList(RulesParser.TypedAttributeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#typedAttributeList}.
	 * @param ctx the parse tree
	 */
	void exitTypedAttributeList(RulesParser.TypedAttributeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(RulesParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(RulesParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link RulesParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(RulesParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link RulesParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(RulesParser.LiteralContext ctx);
}