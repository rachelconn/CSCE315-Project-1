package project1.antlr4;
import org.antlr.v4.runtime.tree.ParseTree;
import java.util.*;
import DBMS;

public class MyRulesBaseListener extends RulesBaseListener {

    private DBMS myDBMS = new DBMS();

    public MyRulesBaseListener() {
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

        if(children.get(5).getText() == "RELATION"){
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

}


