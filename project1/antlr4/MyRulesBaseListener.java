package project1.antlr4;
import org.antlr.v4.runtime.tree.ParseTree;
import java.util.*;
import project1.DBMS;
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
    public Conditional parseComparison(org.antlr.v4.runtime.tree.ParseTree ctx) throws Exception {
        // check to ensure we are evaluating valid condition tree
        if (!(ctx instanceof RulesParser.ConditionContext) &&
                !(ctx instanceof RulesParser.ConjunctionContext) &&
                !(ctx instanceof RulesParser.ComparisonContext))
        {
            throw new Exception(
                    "FATAL (MyRulesBaseListener.parseComparison): invalid node recursed on: " +
                            ctx.getClass().getSimpleName() +
                            "\nwith text: " +
                            ctx.getText()
            );
        }

        if (ctx.getChildCount() != 1 && ctx.getChildCount() != 3)
        {
            throw new Exception("FATAL (MyRulesBaseListener.parseComparison): unforeseen case occurred");
        }

        if (ctx.getChildCount() == 1)
        {
            return parseComparison(ctx.getChild(0));
        }

        if (ctx.getChildCount() == 3)
        {
            // case ["("] [some stuff] [")"]
            if (ctx.getChild(0).getText().equals("("))
            {
                return parseComparison(ctx.getChild(1));
            }

            // case [left tree] ["||"] [right tree]
            if (ctx.getChild(1).getText().equals("||"))
            {
                return new ConditionBranch(
                        ConditionType.OR,
                        parseComparison(ctx.getChild(0)),
                        parseComparison(ctx.getChild(2))
                );
            }

            // case [left tree] ["&&"] [right tree]
            if (ctx.getChild(1).getText().equals("&&"))
            {
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

                if (ctx.getChild(0) instanceof RulesParser.AttributeNameContext) {
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
    }

    @Override
    public void exitSelection(RulesParser.SelectionContext ctx) {
        super.exitSelection(ctx);
    }

    @Override public void exitShowCmd(RulesParser.ShowCmdContext ctx) {
        //System.out.println("SHOW");
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
            //TODO: parse expr here
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
}


