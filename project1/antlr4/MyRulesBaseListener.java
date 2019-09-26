package project1.antlr4;
import project1.DBMS;

public class MyRulesBaseListener extends RulesBaseListener {
    public MyRulesBaseListener() {
        DBMS myDBMS = new DBMS();
    }
}
