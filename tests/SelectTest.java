import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project1.Column;
import project1.DBMS;
import project1.Table;

import project1.antlr4.MyRulesBaseListener;
import project1.antlr4.RulesLexer;
import project1.antlr4.RulesParser;
import project1.conditional.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class SelectTest {
    public static void main(String[] args) throws Exception {
        List<String> lines = new ArrayList<>();
        lines.add("t1 <- select (kind == \"dog\") tabl;");
        Column c1 = new Column("kind", "VARCHAR(20)");
        Column c2 = new Column("name", "VARCHAR(32)");
        ArrayList<Column> cols = new ArrayList<>();
        cols.add(c1);
        cols.add(c2);
        Table t = new Table("tabl", cols, cols);
        DBMS db = new DBMS();
        ArrayList<String> e1 = new ArrayList<>();
        e1.add("dog");
        e1.add("snoop");
        ArrayList<String> e2 = new ArrayList<>();
        e2.add("cat");
        e2.add("eli");
        ArrayList<String> e3 = new ArrayList<>();
        e3.add("fish");
        e3.add("ryan");
        t.addEntry(e1);
        t.addEntry(e2);
        t.addEntry(e3);
        db.insertCmd(t);

        MyRulesBaseListener listener = new MyRulesBaseListener(db);
        for (String line : lines) {
            CharStream charStream = CharStreams.fromString(line);
            RulesLexer lexer = new RulesLexer(charStream);
            CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
            RulesParser parser = new RulesParser(commonTokenStream);
            RulesParser.ProgramContext programContext = parser.program();
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(listener, programContext);
        }
        listener.printTables();

        db.serializeTables("tables");

        HashMap<String,Table> tables = db.deserializeTables("tables");
        System.out.println("Read " + Integer.toString(tables.size()) + " table from xml file");
        for (Map.Entry<String, Table> entry : tables.entrySet()) {
            System.out.println("Table " + entry.getKey() + " has " + Integer.toString(entry.getValue().getSize()) + " entries");
        }
    }
}