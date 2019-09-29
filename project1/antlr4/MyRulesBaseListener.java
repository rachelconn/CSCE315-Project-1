package project1.antlr4;
import org.antlr.v4.runtime.tree.ParseTree;
import java.util.*;
import project1.DBMS;
import project1.Table;
import project1.conditional.*;

public class MyRulesBaseListener extends RulesBaseListener {

    private DBMS myDBMS = new DBMS();

    public MyRulesBaseListener() {
    }

    /*
    Use this to parse a comparison tree into a conditional evalutator.
    Uses base Exception type since if the exception occurs, a fundamental problem occurred.
     */
    public Conditional parseComparison(org.antlr.v4.runtime.ParserRuleContext ctx) throws Exception {
        // check to ensure we are evaluating valid condition tree
        if (!(ctx instanceof RulesParser.ConditionContext) &&
                !(ctx instanceof RulesParser.ConjunctionContext) &&
                !(ctx instanceof RulesParser.ComparisonContext))
        {
            throw new Exception("FATAL (MyRulesBaseListener.parseComparison): invalid node recursed on");
        }

        if (ctx.getChildCount() != 1 || ctx.getChildCount() != 3)
        {
            throw new Exception("FATAL (MyRulesBaseListener.parseComparison): unforeseen case occurred");
        }

        if (ctx.getChildCount() == 1)
        {
            return parseComparison((org.antlr.v4.runtime.ParserRuleContext) ctx.children.get(0));
        }

        if (ctx.getChildCount() == 3)
        {
            // case ["("] [some stuff] [")"]
            if (ctx.children.get(0).getText() == "(")
            {
                return parseComparison((org.antlr.v4.runtime.ParserRuleContext) ctx.children.get(0));
            }

            // case [left tree] ["||"] [right tree]
            if (ctx.children.get(1).getText() == "||")
            {
                return new ConditionBranch(
                        ConditionType.OR,
                        parseComparison((org.antlr.v4.runtime.ParserRuleContext) ctx.children.get(0)),
                        parseComparison((org.antlr.v4.runtime.ParserRuleContext) ctx.children.get(2))
                );
            }

            // case [left tree] ["&&"] [right tree]
            if (ctx.children.get(1).getText() == "&&")
            {
                return new ConditionBranch(
                        ConditionType.AND,
                        parseComparison((org.antlr.v4.runtime.ParserRuleContext) ctx.children.get(0)),
                        parseComparison((org.antlr.v4.runtime.ParserRuleContext) ctx.children.get(2))
                );
            }

            // case [operand] [operator] [AttributeName] or
            // case [AttributeName] [operator] [operand]
            if (Arrays.asList(">", "<", ">=", "<=", "==").contains(ctx.children.get(1).getText())) {
                String fieldName = "";
                String attributeValue = "";

                if (ctx.children.get(0) instanceof RulesParser.AttributeNameContext) {
                    fieldName = ctx.children.get(0).getText();
                    attributeValue = ctx.children.get(2).getText();
                } else {
                    fieldName = ctx.children.get(2).getText();
                    attributeValue = ctx.children.get(0).getText();
                }

                switch (ctx.children.get(1).getText()) {
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
                    default:
                        throw new Exception("Unsupported operation: " + ctx.children.get(1).getText());
                }
            }
        }
        throw new Exception("What happened? --Hillary Clinton");
    }

    public Table parseAtomicExpr(ParseTree t){
        if(t.getChild(0).getText().equals("(")) {
            return parseExpr(t.getChild(1));
        } else {
            return myDBMS.getTable(t.getChild(0).getText());
        }
    }

    public Table parseUnion(ParseTree t) {
        String tableAName = t.getChild(0).getText();
        String tableBName = t.getChild(2).getText();
        return myDBMS.unionQry(myDBMS.getTable(tableAName), myDBMS.getTable(tableBName));
    }

    public Table parseDifference(ParseTree t) {
        String tableAName = t.getChild(0).getText();
        String tableBName = t.getChild(2).getText();
        return myDBMS.differenceQry(myDBMS.getTable(tableAName), myDBMS.getTable(tableBName));
    }

    public Table parseProduct(ParseTree t) {
        String tableAName = t.getChild(0).getText();
        String tableBName = t.getChild(2).getText();
        return myDBMS.productQry(myDBMS.getTable(tableAName), myDBMS.getTable(tableBName));
    }

    public Table parseNaturalJoin(ParseTree t) {
        String tableAName = t.getChild(0).getText();
        String tableBName = t.getChild(2).getText();
        return myDBMS.naturalJoinQry(myDBMS.getTable(tableAName), myDBMS.getTable(tableBName));
    }

    public Table parseExpr(ParseTree t){
        // c is the rule that the expression parser found directly beneath the expr rule
        String c = t.getChild(0).getClass().toString();
        c = c.substring(34,c.length()-7);
        switch(c){
            case "AtomicExpr" :
                return parseAtomicExpr(t.getChild(0));
            case "Selection" : break;
            case "Projection" :
                ParseTree atomicExprTree = t.getChild(4);
                Table projectTable = parseAtomicExpr(atomicExprTree);
                ParseTree attributeTree = t.getChild(2);
                ArrayList<String> attributeNames = parseAttributeList(attributeTree);
                return myDBMS.projectQry(projectTable, attributeNames);
            case "Renaming" :
                ParseTree exprTree = t.getChild(4);
                Table renameTable = parseExpr(exprTree);
                ArrayList<String> newAttNames = parseAttributeList(t.getChild(2));
                return myDBMS.renameQry(renameTable, newAttNames);
            case "Union" : break;
            case "Difference" : break;
            case "Product" : break;
            case "NaturalJoin" : break;
        }
        return null;
    }

    @Override public void exitQuery(RulesParser.QueryContext ctx) {    }

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

        ArrayList<String> primaryKeys = new ArrayList<>();
        ParseTree attList = children.get(9);
        int attCount = (attList.getChildCount() + 1) / 2;
        for(int i = 0 ; i < attCount ; i++){
            String pKey = attList.getChild(2*i).getText();
            primaryKeys.add(pKey);
        }

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


