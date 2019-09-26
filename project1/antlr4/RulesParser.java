// Generated from C:/Users/asus/IdeaProjects/SQLParser/src/project1\Rules.g4 by ANTLR 4.7.2
package project1.antlr4;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class RulesParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, IDENTIFIER=34, OP=35, INTEGER=36, ALPHA=37, DIGIT=38, 
		STRING_LITERAL=39, WS=40;
	public static final int
		RULE_program = 0, RULE_query = 1, RULE_relationName = 2, RULE_attributeName = 3, 
		RULE_expr = 4, RULE_condition = 5, RULE_conjunction = 6, RULE_comparison = 7, 
		RULE_operand = 8, RULE_attributeList = 9, RULE_atomicExpr = 10, RULE_command = 11, 
		RULE_openCmd = 12, RULE_closeCmd = 13, RULE_writeCmd = 14, RULE_exitCmd = 15, 
		RULE_showCmd = 16, RULE_createCmd = 17, RULE_updateCmd = 18, RULE_insertCmd = 19, 
		RULE_deleteCmd = 20, RULE_typedAttributeList = 21, RULE_type = 22, RULE_literal = 23;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "query", "relationName", "attributeName", "expr", "condition", 
			"conjunction", "comparison", "operand", "attributeList", "atomicExpr", 
			"command", "openCmd", "closeCmd", "writeCmd", "exitCmd", "showCmd", "createCmd", 
			"updateCmd", "insertCmd", "deleteCmd", "typedAttributeList", "type", 
			"literal"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'<-'", "'select ('", "')'", "'project ('", "'rename ('", "'+'", 
			"'-'", "'*'", "'&'", "'||'", "'&&'", "'('", "', '", "'OPEN '", "'CLOSE '", 
			"'WRITE '", "'EXIT'", "'SHOW '", "'CREATE TABLE '", "' ('", "') PRIMARY KEY ('", 
			"'UPDATE '", "' SET '", "'='", "','", "' WHERE'", "'INSERT INTO '", "' VALUES FROM '", 
			"'RELATION '", "'DELETE FROM '", "' WHERE '", "'VARCHAR('", "'INTEGER'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, "IDENTIFIER", 
			"OP", "INTEGER", "ALPHA", "DIGIT", "STRING_LITERAL", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Rules.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public RulesParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public List<QueryContext> query() {
			return getRuleContexts(QueryContext.class);
		}
		public QueryContext query(int i) {
			return getRuleContext(QueryContext.class,i);
		}
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(50);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case IDENTIFIER:
					{
					setState(48);
					query();
					}
					break;
				case T__13:
				case T__14:
				case T__15:
				case T__16:
				case T__17:
				case T__18:
				case T__21:
				case T__26:
				case T__29:
					{
					setState(49);
					command();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(52); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__21) | (1L << T__26) | (1L << T__29) | (1L << IDENTIFIER))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QueryContext extends ParserRuleContext {
		public RelationNameContext relationName() {
			return getRuleContext(RelationNameContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public QueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_query; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryContext query() throws RecognitionException {
		QueryContext _localctx = new QueryContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_query);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			relationName();
			setState(55);
			match(T__0);
			setState(56);
			expr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelationNameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(RulesParser.IDENTIFIER, 0); }
		public RelationNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterRelationName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitRelationName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitRelationName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationNameContext relationName() throws RecognitionException {
		RelationNameContext _localctx = new RelationNameContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_relationName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttributeNameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(RulesParser.IDENTIFIER, 0); }
		public AttributeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributeName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterAttributeName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitAttributeName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitAttributeName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeNameContext attributeName() throws RecognitionException {
		AttributeNameContext _localctx = new AttributeNameContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_attributeName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public List<AtomicExprContext> atomicExpr() {
			return getRuleContexts(AtomicExprContext.class);
		}
		public AtomicExprContext atomicExpr(int i) {
			return getRuleContext(AtomicExprContext.class,i);
		}
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public AttributeListContext attributeList() {
			return getRuleContext(AttributeListContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_expr);
		try {
			setState(94);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(62);
				atomicExpr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(63);
				match(T__1);
				setState(64);
				condition();
				setState(65);
				match(T__2);
				setState(66);
				atomicExpr();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(68);
				match(T__3);
				setState(69);
				attributeList();
				setState(70);
				match(T__2);
				setState(71);
				atomicExpr();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(73);
				match(T__4);
				setState(74);
				attributeList();
				setState(75);
				match(T__2);
				setState(76);
				atomicExpr();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(78);
				atomicExpr();
				setState(79);
				match(T__5);
				setState(80);
				atomicExpr();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(82);
				atomicExpr();
				setState(83);
				match(T__6);
				setState(84);
				atomicExpr();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(86);
				atomicExpr();
				setState(87);
				match(T__7);
				setState(88);
				atomicExpr();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(90);
				atomicExpr();
				setState(91);
				match(T__8);
				setState(92);
				atomicExpr();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionContext extends ParserRuleContext {
		public List<ConjunctionContext> conjunction() {
			return getRuleContexts(ConjunctionContext.class);
		}
		public ConjunctionContext conjunction(int i) {
			return getRuleContext(ConjunctionContext.class,i);
		}
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_condition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			conjunction();
			setState(101);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(97);
				match(T__9);
				setState(98);
				conjunction();
				}
				}
				setState(103);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConjunctionContext extends ParserRuleContext {
		public List<ComparisonContext> comparison() {
			return getRuleContexts(ComparisonContext.class);
		}
		public ComparisonContext comparison(int i) {
			return getRuleContext(ComparisonContext.class,i);
		}
		public ConjunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conjunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterConjunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitConjunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitConjunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConjunctionContext conjunction() throws RecognitionException {
		ConjunctionContext _localctx = new ConjunctionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_conjunction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			comparison();
			setState(109);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__10) {
				{
				{
				setState(105);
				match(T__10);
				setState(106);
				comparison();
				}
				}
				setState(111);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComparisonContext extends ParserRuleContext {
		public List<OperandContext> operand() {
			return getRuleContexts(OperandContext.class);
		}
		public OperandContext operand(int i) {
			return getRuleContext(OperandContext.class,i);
		}
		public TerminalNode OP() { return getToken(RulesParser.OP, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public ComparisonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparison; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterComparison(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitComparison(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitComparison(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonContext comparison() throws RecognitionException {
		ComparisonContext _localctx = new ComparisonContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_comparison);
		try {
			setState(120);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
			case INTEGER:
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(112);
				operand();
				setState(113);
				match(OP);
				setState(114);
				operand();
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 2);
				{
				setState(116);
				match(T__11);
				setState(117);
				condition();
				setState(118);
				match(T__2);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperandContext extends ParserRuleContext {
		public AttributeNameContext attributeName() {
			return getRuleContext(AttributeNameContext.class,0);
		}
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public OperandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operand; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterOperand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitOperand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitOperand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperandContext operand() throws RecognitionException {
		OperandContext _localctx = new OperandContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_operand);
		try {
			setState(124);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(122);
				attributeName();
				}
				break;
			case INTEGER:
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(123);
				literal();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttributeListContext extends ParserRuleContext {
		public List<AttributeNameContext> attributeName() {
			return getRuleContexts(AttributeNameContext.class);
		}
		public AttributeNameContext attributeName(int i) {
			return getRuleContext(AttributeNameContext.class,i);
		}
		public AttributeListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributeList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterAttributeList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitAttributeList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitAttributeList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeListContext attributeList() throws RecognitionException {
		AttributeListContext _localctx = new AttributeListContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_attributeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			attributeName();
			setState(131);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__12) {
				{
				{
				setState(127);
				match(T__12);
				setState(128);
				attributeName();
				}
				}
				setState(133);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomicExprContext extends ParserRuleContext {
		public RelationNameContext relationName() {
			return getRuleContext(RelationNameContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AtomicExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomicExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterAtomicExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitAtomicExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitAtomicExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomicExprContext atomicExpr() throws RecognitionException {
		AtomicExprContext _localctx = new AtomicExprContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_atomicExpr);
		try {
			setState(139);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(134);
				relationName();
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 2);
				{
				setState(135);
				match(T__11);
				setState(136);
				expr();
				setState(137);
				match(T__2);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CommandContext extends ParserRuleContext {
		public OpenCmdContext openCmd() {
			return getRuleContext(OpenCmdContext.class,0);
		}
		public CloseCmdContext closeCmd() {
			return getRuleContext(CloseCmdContext.class,0);
		}
		public WriteCmdContext writeCmd() {
			return getRuleContext(WriteCmdContext.class,0);
		}
		public ExitCmdContext exitCmd() {
			return getRuleContext(ExitCmdContext.class,0);
		}
		public ShowCmdContext showCmd() {
			return getRuleContext(ShowCmdContext.class,0);
		}
		public CreateCmdContext createCmd() {
			return getRuleContext(CreateCmdContext.class,0);
		}
		public UpdateCmdContext updateCmd() {
			return getRuleContext(UpdateCmdContext.class,0);
		}
		public InsertCmdContext insertCmd() {
			return getRuleContext(InsertCmdContext.class,0);
		}
		public DeleteCmdContext deleteCmd() {
			return getRuleContext(DeleteCmdContext.class,0);
		}
		public CommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_command; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitCommand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommandContext command() throws RecognitionException {
		CommandContext _localctx = new CommandContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_command);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(150);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__13:
				{
				setState(141);
				openCmd();
				}
				break;
			case T__14:
				{
				setState(142);
				closeCmd();
				}
				break;
			case T__15:
				{
				setState(143);
				writeCmd();
				}
				break;
			case T__16:
				{
				setState(144);
				exitCmd();
				}
				break;
			case T__17:
				{
				setState(145);
				showCmd();
				}
				break;
			case T__18:
				{
				setState(146);
				createCmd();
				}
				break;
			case T__21:
				{
				setState(147);
				updateCmd();
				}
				break;
			case T__26:
				{
				setState(148);
				insertCmd();
				}
				break;
			case T__29:
				{
				setState(149);
				deleteCmd();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OpenCmdContext extends ParserRuleContext {
		public RelationNameContext relationName() {
			return getRuleContext(RelationNameContext.class,0);
		}
		public OpenCmdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_openCmd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterOpenCmd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitOpenCmd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitOpenCmd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OpenCmdContext openCmd() throws RecognitionException {
		OpenCmdContext _localctx = new OpenCmdContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_openCmd);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(152);
			match(T__13);
			setState(153);
			relationName();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CloseCmdContext extends ParserRuleContext {
		public RelationNameContext relationName() {
			return getRuleContext(RelationNameContext.class,0);
		}
		public CloseCmdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_closeCmd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterCloseCmd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitCloseCmd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitCloseCmd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CloseCmdContext closeCmd() throws RecognitionException {
		CloseCmdContext _localctx = new CloseCmdContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_closeCmd);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155);
			match(T__14);
			setState(156);
			relationName();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WriteCmdContext extends ParserRuleContext {
		public RelationNameContext relationName() {
			return getRuleContext(RelationNameContext.class,0);
		}
		public WriteCmdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_writeCmd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterWriteCmd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitWriteCmd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitWriteCmd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WriteCmdContext writeCmd() throws RecognitionException {
		WriteCmdContext _localctx = new WriteCmdContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_writeCmd);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			match(T__15);
			setState(159);
			relationName();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExitCmdContext extends ParserRuleContext {
		public ExitCmdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exitCmd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterExitCmd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitExitCmd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitExitCmd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExitCmdContext exitCmd() throws RecognitionException {
		ExitCmdContext _localctx = new ExitCmdContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_exitCmd);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(161);
			match(T__16);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ShowCmdContext extends ParserRuleContext {
		public AtomicExprContext atomicExpr() {
			return getRuleContext(AtomicExprContext.class,0);
		}
		public ShowCmdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_showCmd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterShowCmd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitShowCmd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitShowCmd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShowCmdContext showCmd() throws RecognitionException {
		ShowCmdContext _localctx = new ShowCmdContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_showCmd);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
			match(T__17);
			setState(164);
			atomicExpr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CreateCmdContext extends ParserRuleContext {
		public RelationNameContext relationName() {
			return getRuleContext(RelationNameContext.class,0);
		}
		public TypedAttributeListContext typedAttributeList() {
			return getRuleContext(TypedAttributeListContext.class,0);
		}
		public AttributeListContext attributeList() {
			return getRuleContext(AttributeListContext.class,0);
		}
		public CreateCmdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createCmd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterCreateCmd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitCreateCmd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitCreateCmd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateCmdContext createCmd() throws RecognitionException {
		CreateCmdContext _localctx = new CreateCmdContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_createCmd);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
			match(T__18);
			setState(167);
			relationName();
			setState(168);
			match(T__19);
			setState(169);
			typedAttributeList();
			setState(170);
			match(T__20);
			setState(171);
			attributeList();
			setState(172);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UpdateCmdContext extends ParserRuleContext {
		public RelationNameContext relationName() {
			return getRuleContext(RelationNameContext.class,0);
		}
		public List<AttributeNameContext> attributeName() {
			return getRuleContexts(AttributeNameContext.class);
		}
		public AttributeNameContext attributeName(int i) {
			return getRuleContext(AttributeNameContext.class,i);
		}
		public List<LiteralContext> literal() {
			return getRuleContexts(LiteralContext.class);
		}
		public LiteralContext literal(int i) {
			return getRuleContext(LiteralContext.class,i);
		}
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public UpdateCmdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_updateCmd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterUpdateCmd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitUpdateCmd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitUpdateCmd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UpdateCmdContext updateCmd() throws RecognitionException {
		UpdateCmdContext _localctx = new UpdateCmdContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_updateCmd);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(174);
			match(T__21);
			setState(175);
			relationName();
			setState(176);
			match(T__22);
			setState(177);
			attributeName();
			setState(178);
			match(T__23);
			setState(179);
			literal();
			setState(187);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__24) {
				{
				{
				setState(180);
				match(T__24);
				setState(181);
				attributeName();
				setState(182);
				match(T__23);
				setState(183);
				literal();
				}
				}
				setState(189);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(190);
			match(T__25);
			setState(191);
			condition();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InsertCmdContext extends ParserRuleContext {
		public RelationNameContext relationName() {
			return getRuleContext(RelationNameContext.class,0);
		}
		public List<LiteralContext> literal() {
			return getRuleContexts(LiteralContext.class);
		}
		public LiteralContext literal(int i) {
			return getRuleContext(LiteralContext.class,i);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public InsertCmdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_insertCmd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterInsertCmd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitInsertCmd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitInsertCmd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InsertCmdContext insertCmd() throws RecognitionException {
		InsertCmdContext _localctx = new InsertCmdContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_insertCmd);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			match(T__26);
			setState(194);
			relationName();
			setState(195);
			match(T__27);
			setState(209);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__11:
				{
				setState(196);
				match(T__11);
				setState(197);
				literal();
				setState(202);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__24) {
					{
					{
					setState(198);
					match(T__24);
					setState(199);
					literal();
					}
					}
					setState(204);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(205);
				match(T__2);
				}
				break;
			case T__28:
				{
				setState(207);
				match(T__28);
				setState(208);
				expr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeleteCmdContext extends ParserRuleContext {
		public RelationNameContext relationName() {
			return getRuleContext(RelationNameContext.class,0);
		}
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public DeleteCmdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_deleteCmd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterDeleteCmd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitDeleteCmd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitDeleteCmd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeleteCmdContext deleteCmd() throws RecognitionException {
		DeleteCmdContext _localctx = new DeleteCmdContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_deleteCmd);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(211);
			match(T__29);
			setState(212);
			relationName();
			setState(213);
			match(T__30);
			setState(214);
			condition();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypedAttributeListContext extends ParserRuleContext {
		public List<AttributeNameContext> attributeName() {
			return getRuleContexts(AttributeNameContext.class);
		}
		public AttributeNameContext attributeName(int i) {
			return getRuleContext(AttributeNameContext.class,i);
		}
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public TypedAttributeListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typedAttributeList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterTypedAttributeList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitTypedAttributeList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitTypedAttributeList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypedAttributeListContext typedAttributeList() throws RecognitionException {
		TypedAttributeListContext _localctx = new TypedAttributeListContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_typedAttributeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216);
			attributeName();
			setState(217);
			type();
			setState(224);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__24) {
				{
				{
				setState(218);
				match(T__24);
				setState(219);
				attributeName();
				setState(220);
				type();
				}
				}
				setState(226);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(RulesParser.INTEGER, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_type);
		try {
			setState(231);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__31:
				enterOuterAlt(_localctx, 1);
				{
				setState(227);
				match(T__31);
				setState(228);
				match(INTEGER);
				setState(229);
				match(T__2);
				}
				break;
			case T__32:
				enterOuterAlt(_localctx, 2);
				{
				setState(230);
				match(T__32);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode STRING_LITERAL() { return getToken(RulesParser.STRING_LITERAL, 0); }
		public TerminalNode INTEGER() { return getToken(RulesParser.INTEGER, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RulesListener ) ((RulesListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RulesVisitor ) return ((RulesVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(233);
			_la = _input.LA(1);
			if ( !(_la==INTEGER || _la==STRING_LITERAL) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3*\u00ee\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\3\2\3\2\6\2\65\n\2\r\2\16\2\66\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6a\n\6\3\7\3\7"+
		"\3\7\7\7f\n\7\f\7\16\7i\13\7\3\b\3\b\3\b\7\bn\n\b\f\b\16\bq\13\b\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t{\n\t\3\n\3\n\5\n\177\n\n\3\13\3\13\3\13"+
		"\7\13\u0084\n\13\f\13\16\13\u0087\13\13\3\f\3\f\3\f\3\f\3\f\5\f\u008e"+
		"\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u0099\n\r\3\16\3\16\3\16"+
		"\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\7\24\u00bc\n\24\f\24\16\24\u00bf\13\24\3\24\3\24\3\24\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\7\25\u00cb\n\25\f\25\16\25\u00ce\13\25"+
		"\3\25\3\25\3\25\3\25\5\25\u00d4\n\25\3\26\3\26\3\26\3\26\3\26\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\7\27\u00e1\n\27\f\27\16\27\u00e4\13\27\3\30\3\30"+
		"\3\30\3\30\5\30\u00ea\n\30\3\31\3\31\3\31\2\2\32\2\4\6\b\n\f\16\20\22"+
		"\24\26\30\32\34\36 \"$&(*,.\60\2\3\4\2&&))\2\u00f1\2\64\3\2\2\2\48\3\2"+
		"\2\2\6<\3\2\2\2\b>\3\2\2\2\n`\3\2\2\2\fb\3\2\2\2\16j\3\2\2\2\20z\3\2\2"+
		"\2\22~\3\2\2\2\24\u0080\3\2\2\2\26\u008d\3\2\2\2\30\u0098\3\2\2\2\32\u009a"+
		"\3\2\2\2\34\u009d\3\2\2\2\36\u00a0\3\2\2\2 \u00a3\3\2\2\2\"\u00a5\3\2"+
		"\2\2$\u00a8\3\2\2\2&\u00b0\3\2\2\2(\u00c3\3\2\2\2*\u00d5\3\2\2\2,\u00da"+
		"\3\2\2\2.\u00e9\3\2\2\2\60\u00eb\3\2\2\2\62\65\5\4\3\2\63\65\5\30\r\2"+
		"\64\62\3\2\2\2\64\63\3\2\2\2\65\66\3\2\2\2\66\64\3\2\2\2\66\67\3\2\2\2"+
		"\67\3\3\2\2\289\5\6\4\29:\7\3\2\2:;\5\n\6\2;\5\3\2\2\2<=\7$\2\2=\7\3\2"+
		"\2\2>?\7$\2\2?\t\3\2\2\2@a\5\26\f\2AB\7\4\2\2BC\5\f\7\2CD\7\5\2\2DE\5"+
		"\26\f\2Ea\3\2\2\2FG\7\6\2\2GH\5\24\13\2HI\7\5\2\2IJ\5\26\f\2Ja\3\2\2\2"+
		"KL\7\7\2\2LM\5\24\13\2MN\7\5\2\2NO\5\26\f\2Oa\3\2\2\2PQ\5\26\f\2QR\7\b"+
		"\2\2RS\5\26\f\2Sa\3\2\2\2TU\5\26\f\2UV\7\t\2\2VW\5\26\f\2Wa\3\2\2\2XY"+
		"\5\26\f\2YZ\7\n\2\2Z[\5\26\f\2[a\3\2\2\2\\]\5\26\f\2]^\7\13\2\2^_\5\26"+
		"\f\2_a\3\2\2\2`@\3\2\2\2`A\3\2\2\2`F\3\2\2\2`K\3\2\2\2`P\3\2\2\2`T\3\2"+
		"\2\2`X\3\2\2\2`\\\3\2\2\2a\13\3\2\2\2bg\5\16\b\2cd\7\f\2\2df\5\16\b\2"+
		"ec\3\2\2\2fi\3\2\2\2ge\3\2\2\2gh\3\2\2\2h\r\3\2\2\2ig\3\2\2\2jo\5\20\t"+
		"\2kl\7\r\2\2ln\5\20\t\2mk\3\2\2\2nq\3\2\2\2om\3\2\2\2op\3\2\2\2p\17\3"+
		"\2\2\2qo\3\2\2\2rs\5\22\n\2st\7%\2\2tu\5\22\n\2u{\3\2\2\2vw\7\16\2\2w"+
		"x\5\f\7\2xy\7\5\2\2y{\3\2\2\2zr\3\2\2\2zv\3\2\2\2{\21\3\2\2\2|\177\5\b"+
		"\5\2}\177\5\60\31\2~|\3\2\2\2~}\3\2\2\2\177\23\3\2\2\2\u0080\u0085\5\b"+
		"\5\2\u0081\u0082\7\17\2\2\u0082\u0084\5\b\5\2\u0083\u0081\3\2\2\2\u0084"+
		"\u0087\3\2\2\2\u0085\u0083\3\2\2\2\u0085\u0086\3\2\2\2\u0086\25\3\2\2"+
		"\2\u0087\u0085\3\2\2\2\u0088\u008e\5\6\4\2\u0089\u008a\7\16\2\2\u008a"+
		"\u008b\5\n\6\2\u008b\u008c\7\5\2\2\u008c\u008e\3\2\2\2\u008d\u0088\3\2"+
		"\2\2\u008d\u0089\3\2\2\2\u008e\27\3\2\2\2\u008f\u0099\5\32\16\2\u0090"+
		"\u0099\5\34\17\2\u0091\u0099\5\36\20\2\u0092\u0099\5 \21\2\u0093\u0099"+
		"\5\"\22\2\u0094\u0099\5$\23\2\u0095\u0099\5&\24\2\u0096\u0099\5(\25\2"+
		"\u0097\u0099\5*\26\2\u0098\u008f\3\2\2\2\u0098\u0090\3\2\2\2\u0098\u0091"+
		"\3\2\2\2\u0098\u0092\3\2\2\2\u0098\u0093\3\2\2\2\u0098\u0094\3\2\2\2\u0098"+
		"\u0095\3\2\2\2\u0098\u0096\3\2\2\2\u0098\u0097\3\2\2\2\u0099\31\3\2\2"+
		"\2\u009a\u009b\7\20\2\2\u009b\u009c\5\6\4\2\u009c\33\3\2\2\2\u009d\u009e"+
		"\7\21\2\2\u009e\u009f\5\6\4\2\u009f\35\3\2\2\2\u00a0\u00a1\7\22\2\2\u00a1"+
		"\u00a2\5\6\4\2\u00a2\37\3\2\2\2\u00a3\u00a4\7\23\2\2\u00a4!\3\2\2\2\u00a5"+
		"\u00a6\7\24\2\2\u00a6\u00a7\5\26\f\2\u00a7#\3\2\2\2\u00a8\u00a9\7\25\2"+
		"\2\u00a9\u00aa\5\6\4\2\u00aa\u00ab\7\26\2\2\u00ab\u00ac\5,\27\2\u00ac"+
		"\u00ad\7\27\2\2\u00ad\u00ae\5\24\13\2\u00ae\u00af\7\5\2\2\u00af%\3\2\2"+
		"\2\u00b0\u00b1\7\30\2\2\u00b1\u00b2\5\6\4\2\u00b2\u00b3\7\31\2\2\u00b3"+
		"\u00b4\5\b\5\2\u00b4\u00b5\7\32\2\2\u00b5\u00bd\5\60\31\2\u00b6\u00b7"+
		"\7\33\2\2\u00b7\u00b8\5\b\5\2\u00b8\u00b9\7\32\2\2\u00b9\u00ba\5\60\31"+
		"\2\u00ba\u00bc\3\2\2\2\u00bb\u00b6\3\2\2\2\u00bc\u00bf\3\2\2\2\u00bd\u00bb"+
		"\3\2\2\2\u00bd\u00be\3\2\2\2\u00be\u00c0\3\2\2\2\u00bf\u00bd\3\2\2\2\u00c0"+
		"\u00c1\7\34\2\2\u00c1\u00c2\5\f\7\2\u00c2\'\3\2\2\2\u00c3\u00c4\7\35\2"+
		"\2\u00c4\u00c5\5\6\4\2\u00c5\u00d3\7\36\2\2\u00c6\u00c7\7\16\2\2\u00c7"+
		"\u00cc\5\60\31\2\u00c8\u00c9\7\33\2\2\u00c9\u00cb\5\60\31\2\u00ca\u00c8"+
		"\3\2\2\2\u00cb\u00ce\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cd"+
		"\u00cf\3\2\2\2\u00ce\u00cc\3\2\2\2\u00cf\u00d0\7\5\2\2\u00d0\u00d4\3\2"+
		"\2\2\u00d1\u00d2\7\37\2\2\u00d2\u00d4\5\n\6\2\u00d3\u00c6\3\2\2\2\u00d3"+
		"\u00d1\3\2\2\2\u00d4)\3\2\2\2\u00d5\u00d6\7 \2\2\u00d6\u00d7\5\6\4\2\u00d7"+
		"\u00d8\7!\2\2\u00d8\u00d9\5\f\7\2\u00d9+\3\2\2\2\u00da\u00db\5\b\5\2\u00db"+
		"\u00e2\5.\30\2\u00dc\u00dd\7\33\2\2\u00dd\u00de\5\b\5\2\u00de\u00df\5"+
		".\30\2\u00df\u00e1\3\2\2\2\u00e0\u00dc\3\2\2\2\u00e1\u00e4\3\2\2\2\u00e2"+
		"\u00e0\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3-\3\2\2\2\u00e4\u00e2\3\2\2\2"+
		"\u00e5\u00e6\7\"\2\2\u00e6\u00e7\7&\2\2\u00e7\u00ea\7\5\2\2\u00e8\u00ea"+
		"\7#\2\2\u00e9\u00e5\3\2\2\2\u00e9\u00e8\3\2\2\2\u00ea/\3\2\2\2\u00eb\u00ec"+
		"\t\2\2\2\u00ec\61\3\2\2\2\21\64\66`goz~\u0085\u008d\u0098\u00bd\u00cc"+
		"\u00d3\u00e2\u00e9";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}