package project1.antlr4;
import org.antlr.v4.runtime.tree.ParseTree;
import java.util.*;
import project1.DBMS;
import project1.Table;
import project1.conditional.*;

public class MyRulesBaseListener extends RulesBaseListener {

    private DBMS myDBMS;

    public MyRulesBaseListener() {
        myDBMS = new DBMS();
    }

    public MyRulesBaseListener(DBMS db)
    {
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

            if (ctx.getChildCount() != 1 && ctx.getChildCount() != 3) {
                throw new Exception("FATAL (MyRulesBaseListener.parseComparison): unforeseen case occurred");
            }

            if (ctx.getChildCount() == 1) {
                return parseComparison(ctx.getChild(0));
            }

            if (ctx.getChildCount() == 3) {
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
                    String fieldName = "";
                    String attributeValue = "";

                    if (ctx.getChild(0).getChild(0) instanceof RulesParser.AttributeNameContext) {
                        fieldName = ctx.getChild(0).getText();
                        attributeValue = ctx.getChild(2).getText();
                    } else {
                        fieldName = ctx.getChild(2).getText();
                        attributeValue = ctx.getChild(0).getText();
                    }

                    switch (ctx.getChild(1).getText()) {
                        case ">":
                            return new GreaterThanComparison(
                                    Conditional.getType(attributeValue),
                                    attributeValue,
                                    fieldName
                            );
                        case "<":
                            return new LessThanComparison(
                                    Conditional.getType(attributeValue),
                                    attributeValue,
                                    fieldName
                            );
                        case ">=":
                            return new GreaterThanEqualsComparison(
                                    Conditional.getType(attributeValue),
                                    attributeValue,
                                    fieldName
                            );
                        case "<=":
                            return new LessThanEqualsComparison(
                                    Conditional.getType(attributeValue),
                                    attributeValue,
                                    fieldName
                            );
                        case "==":
                            return new EqualsComparison(
                                    Conditional.getType(attributeValue),
                                    attributeValue,
                                    fieldName
                            );
                        case "!=":
                            return new NotEqualsComparison(
                                    Conditional.getType(attributeValue),
                                    attributeValue,
                                    fieldName
                            );
                        default:
                            throw new Exception("Unsupported operation: " + ctx.getChild(1).getText());
                    }
                }
            }
            throw new Exception("What happened? --Hillary Clinton");
        } catch(Exception e) {
            System.out.println("Exception in parseComparison: \n" + String.valueOf(e));
            return null;
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

    public Table parseExpr(ParseTree t){
        // c is the rule that the expression parser found directly beneath the expr rule
        String c = t.getChild(0).getClass().toString();
        c = c.substring(34,c.length()-7);
        ParseTree ruleContext = t.getChild(0);
        switch(c){
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

    @Override public void exitQuery(RulesParser.QueryContext ctx) {
        List<ParseTree> children = ctx.children;
        String tableName = children.get(0).getText();
        Table t = parseExpr(children.get(2));
        t.setName(tableName);
        myDBMS.addTable(t);
    }

    @Override
    public void exitSelection(RulesParser.SelectionContext ctx) {
        super.exitSelection(ctx);
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
        for(int i = 0 ; i < typedAttCount ; i++){
            String attName = typedAttList.getChild(i*3).getText();
            String type = typedAttList.getChild(i*3 + 1).getText();
            attributes.put(attName,type);
        }

        ArrayList<String> primaryKeys = parseAttributeList(children.get(9));

        myDBMS.createCmd(tableName, attributes, primaryKeys);
    }

    @Override public void exitInsertCmd(RulesParser.InsertCmdContext ctx) {
        List<ParseTree> children = ctx.children;
        String tableName = children.get(2).getText();
        ArrayList<String> attributes = new ArrayList<>();

        if(children.get(5).getText().equals("RELATION")){
            myDBMS.insertCmd(tableName, parseExpr(children.get(6)));
        } else {
            //6 is the first index that a literal shows up
            for(int i = 6 ; i < ctx.getChildCount() ; i += 2){
                String str = children.get(i).getText();
                // If the literal is a string, remove the quotations
                if(str.charAt(0) == '"'){
                    str = str.substring(1,str.length() - 1);
                }
                attributes.add(str);
            }

            myDBMS.insertCmd(tableName, attributes);
        }
    }

    @Override public void exitUnion(RulesParser.UnionContext ctx) {

    }

    /*
    A testing method for our glorious super anti parsing thing no dijkstra's crap 100% legit
     */
    @Override
    public void exitComparison(RulesParser.ComparisonContext ctx) {
        // comparison debugging
        if (false) {
            try {
                Conditional cond = parseComparison(ctx);
                System.out.println(ctx.getText() + " -> SUCCESS");
            }
            catch (Exception ex) {
                System.out.println(ex);
                System.out.println(ctx.getText() + " -> FAIL");
            }
        }
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
}


