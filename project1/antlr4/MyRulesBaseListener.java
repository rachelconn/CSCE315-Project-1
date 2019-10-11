package project1.antlr4;
import org.antlr.v4.runtime.tree.ParseTree;
import java.util.*;
import javafx.util.Pair;
import project1.DBMS;
import project1.Table;
import project1.conditional.*;

public class MyRulesBaseListener extends RulesBaseListener {

    private DBMS myDBMS;
    private Table table;

    public MyRulesBaseListener() {
        myDBMS = new DBMS();
    }

    public MyRulesBaseListener(DBMS db) {
        myDBMS = db;
    }

    /*
    Use this to parse a comparison tree into a conditional evalutator.
    Uses base Exception type since if the exception occurs, a fundamental problem occurred.
     */
    public Conditional parseComparison(org.antlr.v4.runtime.tree.ParseTree ctx) {
        try {
            // check to ensure we are evaluating valid condition tree
            if (!(ctx instanceof RulesParser.ConditionContext) &&
                    !(ctx instanceof RulesParser.ConjunctionContext) &&
                    !(ctx instanceof RulesParser.ComparisonContext)) {
                throw new Exception(
                        "FATAL (MyRulesBaseListener.parseComparison): invalid node recursed on: " +
                                ctx.getClass().getSimpleName() +
                                "\nwith text: " +
                                ctx.getText()
                );
            }

            if (ctx.getChildCount() != 1 && ctx.getChildCount() != 3 && ctx.getChildCount() % 2 != 1) {
                throw new Exception("FATAL (MyRulesBaseListener.parseComparison): unforeseen case occurred");
            }

            if (ctx.getChildCount() == 1) {
                return parseComparison(ctx.getChild(0));
            }

            else if (ctx.getChildCount() == 3) {
                // case ["("] [some stuff] [")"]
                if (ctx.getChild(0).getText().equals("(")) {
                    return parseComparison(ctx.getChild(1));
                }

                // case [left tree] ["||"] [right tree]
                if (ctx.getChild(1).getText().equals("||")) {
                    return new ConditionBranch(
                            ConditionType.OR,
                            parseComparison(ctx.getChild(0)),
                            parseComparison(ctx.getChild(2))
                    );
                }

                // case [left tree] ["&&"] [right tree]
                if (ctx.getChild(1).getText().equals("&&")) {
                    return new ConditionBranch(
                            ConditionType.AND,
                            parseComparison(ctx.getChild(0)),
                            parseComparison(ctx.getChild(2))
                    );
                }

                // case [operand] [operator] [AttributeName] or
                // case [AttributeName] [operator] [operand]
                if (Arrays.asList(">", "<", ">=", "<=", "==", "!=").contains(ctx.getChild(1).getText())) {
                    TypedData _left = parseValueLeafIntoTrackedCell(ctx.getChild(0).getChild(0));
                    TypedData _right = parseValueLeafIntoTrackedCell(ctx.getChild(2).getChild(0));  // TODO: check 2-0 or 2-2

                    switch (ctx.getChild(1).getText()) {
                        case ">":
                            return new GreaterThanComparison(
                                    _left,
                                    _right
                            );
                        case "<":
                            return new LessThanComparison(
                                    _left,
                                    _right
                            );
                        case ">=":
                            return new GreaterThanEqualsComparison(
                                    _left,
                                    _right
                            );
                        case "<=":
                            return new LessThanEqualsComparison(
                                    _left,
                                    _right
                            );
                        case "==":
                            return new EqualsComparison(
                                    _left,
                                    _right
                            );
                        case "!=":
                            return new NotEqualsComparison(
                                    _left,
                                    _right
                            );
                        default:
                            throw new Exception("Unsupported operation: " + ctx.getChild(1).getText());
                    }
                }
            }
            else if (ctx.getChildCount() % 2 == 1) {
                Conditional leftSide = parseComparison(ctx.getChild(0));
                for (int i=1; i<ctx.getChildCount(); i+=2)
                {
                    // i is the operator
                    // i+1 is the other conditional
                    // case [left tree] ["||"] [right tree]
                    if (ctx.getChild(i).getText().equals("||")) {
                        leftSide = new ConditionBranch(
                                ConditionType.OR,
                                leftSide,
                                parseComparison(ctx.getChild(i+1))
                        );
                    }

                    // case [left tree] ["&&"] [right tree]
                    if (ctx.getChild(i).getText().equals("&&")) {
                        leftSide = new ConditionBranch(
                                ConditionType.AND,
                                leftSide,
                                parseComparison(ctx.getChild(i+1))
                        );
                    }
                }
                return leftSide;
            }
            throw new Exception("What happened? --Hillary Clinton");
        } catch(Exception e) {
            System.out.println("Exception in parseComparison: \n" + String.valueOf(e));
            return null;
        }
    }

    public TypedData parseValueLeafIntoTrackedCell(ParseTree pt) throws Exception
    {
        if (pt instanceof RulesParser.AttributeNameContext) {
            return new TypedData(ParsedDataType.FIELD, pt.getText());
        }
        else if (pt instanceof RulesParser.LiteralContext) {
            return new TypedData(TypedData.getType(pt.getText()), Utilities.sanitizeFieldName(pt.getText()));
        }
        else {
            throw new Exception("[FATAL] parseValueLeafIntoTrackedCell parsed invalid type: " + pt.getClass().toString());
        }
    }

    public Table parseAtomicExpr(ParseTree t){
        if(t.getChild(0).getText().equals("(")) {
            return parseExpr(t.getChild(1));
        } else {
            return myDBMS.getTable(t.getChild(0).getText());
        }
    }

    public Table parseSelection(ParseTree t) {
        ParseTree atomicExprTree = t.getChild(4);
        Table selectTable = parseAtomicExpr(atomicExprTree);
        ParseTree conditionTree = t.getChild(2);
        Conditional c = parseComparison(conditionTree);
        try {
            return myDBMS.selectQry(selectTable, c);
        }
        catch (IncompatibleTypesException ex) {
            System.out.println("Unsupported operation in: " + t.getText());
            System.out.println("Additional Information: " + ex.toString());
            return new Table("temp", selectTable.getAllColumns(), selectTable.getPrimaryKeys());
        }
    }

    public Table parseProjection(ParseTree t) {
        ParseTree atomicExprTree = t.getChild(4);
        Table projectTable = parseAtomicExpr(atomicExprTree);
        ParseTree attributeTree = t.getChild(2);
        ArrayList<String> attributeNames = parseAttributeList(attributeTree);
        return myDBMS.projectQry(projectTable, attributeNames);
    }

    public Table parseRenaming(ParseTree t) {
        ParseTree exprTree = t.getChild(4);
        Table renameTable = parseAtomicExpr(exprTree);
        ArrayList<String> newAttNames = parseAttributeList(t.getChild(2));
        return myDBMS.renameQry(renameTable, newAttNames);
    }

    public Table parseUnion(ParseTree t) {
        ParseTree exprA = t.getChild(0);
        ParseTree exprB = t.getChild(2);
        return myDBMS.unionQry(parseAtomicExpr(exprA), parseAtomicExpr(exprB));
    }

    public Table parseDifference(ParseTree t) {
        ParseTree exprA = t.getChild(0);
        ParseTree exprB = t.getChild(2);
        return myDBMS.differenceQry(parseAtomicExpr(exprA), parseAtomicExpr(exprB));
    }

    public Table parseProduct(ParseTree t) {
        ParseTree exprA = t.getChild(0);
        ParseTree exprB = t.getChild(2);
        return myDBMS.productQry(parseAtomicExpr(exprA), parseAtomicExpr(exprB));
    }

    public Table parseNaturalJoin(ParseTree t) {
        ParseTree exprA = t.getChild(0);
        ParseTree exprB = t.getChild(2);
        return myDBMS.naturalJoinQry(parseAtomicExpr(exprA), parseAtomicExpr(exprB));
    }

    public Table parseExpr(ParseTree t) {
        // c is the rule that the expression parser found directly beneath the expr rule
        String c = t.getChild(0).getClass().toString();
        c = c.substring(34,c.length()-7);
        ParseTree ruleContext = t.getChild(0);
        switch(c) {
            case "AtomicExpr" :
                return parseAtomicExpr(ruleContext);
            case "Selection" :
                return parseSelection(ruleContext);
            case "Projection" :
                return parseProjection(ruleContext);
            case "Renaming" :
                return parseRenaming(ruleContext);
            case "Union" :
                return parseUnion(ruleContext);
            case "Difference" :
                return parseDifference(ruleContext);
            case "Product" :
                return parseProduct(ruleContext);
            case "NaturalJoin" :
                return parseNaturalJoin(ruleContext);
        }
        return null;
    }

    @Override public void exitSoleExpr(RulesParser.SoleExprContext ctx) {
        table = parseExpr(ctx.getChild(0));
    }

    @Override public void exitQuery(RulesParser.QueryContext ctx) {
        List<ParseTree> children = ctx.children;
        String tableName = children.get(0).getText();
        Table t = parseExpr(children.get(2));
        t.setName(tableName);
        myDBMS.addTable(t);
    }

    @Override public void exitShowCmd(RulesParser.ShowCmdContext ctx) {
        //child(0) : "SHOW" ; child(1) : atomicExpr
        Table t = parseAtomicExpr(ctx.children.get(1));
        myDBMS.showCmd(t);
    }

    @Override public void exitCreateCmd(RulesParser.CreateCmdContext ctx) {
        List<ParseTree> children = ctx.children;
        String tableName = children.get(2).getText();

        LinkedHashMap<String,String> attributes = new LinkedHashMap<>(); //using linkedHashMap to guarantee insertion order
        ParseTree typedAttList = children.get(4);
        int typedAttCount = (typedAttList.getChildCount() + 1) / 3;
        for(int i = 0 ; i < typedAttCount ; i++) {
            String attName = typedAttList.getChild(i*3).getText();
            String type = typedAttList.getChild(i*3 + 1).getText();
            attributes.put(attName,type);
        }

        ArrayList<String> primaryKeys = parseAttributeList(children.get(9));

        myDBMS.createCmd(tableName, attributes, primaryKeys);
    }

    @Override public void exitDeleteCmd(RulesParser.DeleteCmdContext ctx) {
        String tableName = ctx.getChild(2).getText();
        ParseTree condition = ctx.getChild(4);
        Conditional comparisonTree = parseComparison(condition);
        try {
            myDBMS.deleteCmd(tableName, comparisonTree);
        } catch (IncompatibleTypesException e) {
            System.out.println("Error in conditional at DELETE branch: incompatible types comparison.");
            return;
        }
    }


    @Override public void exitInsertCmd(RulesParser.InsertCmdContext ctx) {
        List<ParseTree> children = ctx.children;
        String tableName = children.get(2).getText();
        ArrayList<String> attributes = new ArrayList<>();

        if(children.get(5).getText().equals("RELATION")) {
            myDBMS.insertCmd(tableName, parseExpr(children.get(6)));
        } else {
            //6 is the first index that a literal shows up
            for(int i = 6 ; i < ctx.getChildCount() ; i += 2) {
                String str = children.get(i).getText();
                // If the literal is a string, remove the quotations
                if(str.charAt(0) == '"') {
                    str = str.substring(1,str.length() - 1);
                }
                attributes.add(str);
            }

            myDBMS.insertCmd(tableName, attributes);
        }
    }

    @Override public void exitUpdateCmd(RulesParser.UpdateCmdContext ctx) {
        ArrayList<Pair<String, String>> updates = new ArrayList<>();
        String tableName = ctx.getChild(1).getText();
        for(int i = 3 ; i < ctx.getChildCount() - 4 ; i += 4){
            String attName = ctx.getChild(i).getText();
            String literal = ctx.getChild(i+2).getText();
            if(literal.charAt(0) == '"'){
                literal = literal.substring(1,literal.length() - 1);
            }
            Pair<String, String> pair = new Pair<>(attName, literal);
            updates.add(pair);
        }
        ParseTree cTree = ctx.getChild(ctx.getChildCount() - 1);
        Conditional conditionTree = parseComparison(cTree);
        myDBMS.updateCmd(tableName, updates, conditionTree);
    }

    @Override public void exitOpenCmd(RulesParser.OpenCmdContext ctx) {
        myDBMS.openCmd(ctx.getChild(1).getText());
    }

    @Override public void exitCloseCmd(RulesParser.CloseCmdContext ctx) {
        myDBMS.closeCmd(ctx.getChild(1).getText());
    }

    @Override public void exitWriteCmd(RulesParser.WriteCmdContext ctx) {
        myDBMS.writeCmd(ctx.getChild(1).getText());
    }

    @Override public void exitExitCmd(RulesParser.ExitCmdContext ctx) {
        myDBMS.exitCmd();
    }

    public void printTables() {
        myDBMS.printTables();
    }

    public ArrayList<String> parseAttributeList(ParseTree t){
        ArrayList<String> attList = new ArrayList<>();
        for(int i = 0 ; i < t.getChildCount() ; i += 2){
            attList.add(t.getChild(i).getText());
        }
        return attList;
    }

    public Table getTable() {
        return table;
    }

    public DBMS getMyDBMS() {
        return myDBMS;
    }
}