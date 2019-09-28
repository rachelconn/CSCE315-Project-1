package project1.antlr4;
import org.antlr.v4.runtime.tree.ParseTree;
import java.util.*;
import DBMS;

public class MyRulesBaseListener extends RulesBaseListener {

    private DBMS myDBMS = new DBMS();

    public MyRulesBaseListener() {
    }

    @Override public void exitShowCmd(RulesParser.ShowCmdContext ctx) {
        System.out.println("SHOW");
    }

    @Override public void exitCreateCmd(RulesParser.CreateCmdContext ctx) {
        List<ParseTree> children = ctx.children;
        String tableName = children.get(2).getText();
        System.out.println(tableName);
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

}


