package project1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import csce315.project1.*;
import project1.antlr4.MyRulesBaseListener;
import project1.antlr4.RulesLexer;
import project1.antlr4.RulesParser;
import project1.conditional.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;


public class Main {
    public static void main(String[] args) throws IOException {
        MovieDatabaseParser parser = new MovieDatabaseParser();

        List<Movie> moviesList = parser.deserializeMovies("../data/movies_single.json");
        List<Credits> creditsList = parser.deserializeCredits("../data/credits_single.json");

        DBMS myDBMS = new DBMS();

        //Define MOVIES table
        ArrayList<String> attNames = new ArrayList<>();
        attNames.add("id"); attNames.add("title"); attNames.add("genre1");
        attNames.add("genre2"); attNames.add("genre3"); attNames.add("rating");
        ArrayList<String> attTypes = new ArrayList<>();
        attNames.add("INTEGER"); attNames.add("VARCHAR(50)"); attNames.add("INTEGER");
        attNames.add("INTEGER"); attNames.add("INTEGER"); attNames.add("INTEGER");
        ArrayList<Integer> pKeys = new ArrayList<>();
        pKeys.add(0);
        Table movies = new Table("movies",attNames, attTypes, pKeys);
        myDBMS.addTable(movies);

        //define GENRES table
        ArrayList<String> attNames2 = new ArrayList<>();
        attNames2.add("id"); attNames2.add("name");
        ArrayList<String> attTypes2 = new ArrayList<>();
        attNames2.add("INTEGER"); attNames2.add("VARCHAR(20)");
        ArrayList<Integer> pKeys2 = new ArrayList<>();
        pKeys2.add(0);
        Table genresTable = new Table("genres", attNames2, attTypes2, pKeys2);
        myDBMS.addTable(genresTable);
        ArrayList<String> nullGenre = new ArrayList<>();
        //add the null genre
        nullGenre.add("0"); nullGenre.add("NULL");
        myDBMS.insertCmd("genres",nullGenre);

        //parse through each movie in the list
        for(Movie m : moviesList){
            ArrayList<String> attributes = new ArrayList<>();
            int id = m.getId(); attributes.add(Integer.toString(id));
            String title = m.getTitle(); attributes.add(title);
            List<Movie.Genre> movieGenres = m.getGenres();
            //parse through list of genres given (max 3)
            for(Movie.Genre g : movieGenres){
                String gId = Integer.toString(g.getId());
                String gName = g.getName();
                attributes.add(gId);
                //add the genre lookup to the genres table
                ArrayList<String> gAtts = new ArrayList<>();
                gAtts.add(gId); gAtts.add(gName);
            }
            if(movieGenres.size() != 3){
                for(int i = 1 ; i <= 3 - movieGenres.size() ; i++){
                    attributes.add("0");
                }
            }
            //rating is on a scale 1-10 with one decimal point, multiply by 10 to get integer between 1-100
            //round to get rid of any floating point precision error
            int rating = (int) Math.round(m.getVote_average() * 10);
            attributes.add(Integer.toString(rating));
            myDBMS.insertCmd("movies", attributes);
        }

        //unitTesting();
        /*
        File file = new File("project1/input.txt");
        Scanner scanner = new Scanner(file);
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.length() != 0) { lines.add(line); }
        }
        MyRulesBaseListener listener = new MyRulesBaseListener();
        for (String line : lines) {
            CharStream charStream = CharStreams.fromString(line);
            RulesLexer lexer = new RulesLexer(charStream);
            CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
            RulesParser parser = new RulesParser(commonTokenStream);
            RulesParser.ProgramContext programContext = parser.program();
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(listener, programContext);
        }
        */
    }
    /*
    public static void unitTesting() throws FileNotFoundException{
        int numOfTests = 3;
        for(int i = 1 ; i <= numOfTests ; i++) {
            File file = new File("tests/test" + String.valueOf(i) + ".txt");
            Scanner scanner = new Scanner(file);
            List<String> lines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.length() != 0) {
                    lines.add(line);
                }
            }
            MyRulesBaseListener listener = new MyRulesBaseListener();
            for (String line : lines) {
                CharStream charStream = CharStreams.fromString(line);
                RulesLexer lexer = new RulesLexer(charStream);
                CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
                RulesParser parser = new RulesParser(commonTokenStream);
                RulesParser.ProgramContext programContext = parser.program();
                ParseTreeWalker walker = new ParseTreeWalker();
                walker.walk(listener, programContext);
            }
            System.out.println("----------------------");
        }
    }
    */
}