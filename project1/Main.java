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

        DBMS myDBMS = new DBMS();

        List<Movie> moviesList = parser.deserializeMovies("./data/movies_single.json");
        generateMovieTable(moviesList, myDBMS);
        List<Credits> creditsList = parser.deserializeCredits("./data/credits_single.json");
        generateCastTable(creditsList, myDBMS);

    }

    static void generateMovieTable(List<Movie> moviesList, DBMS myDBMS) {
        //Define MOVIES table
        ArrayList<String> attNames = new ArrayList<>();
        attNames.add("id"); attNames.add("title"); attNames.add("rating");
        ArrayList<String> attTypes = new ArrayList<>();
        attTypes.add("INTEGER"); attTypes.add("VARCHAR(50)"); attTypes.add("INTEGER");
        ArrayList<Integer> pKeys = new ArrayList<>();
        pKeys.add(0);
        Table movies = new Table("movies",attNames, attTypes, pKeys);
        myDBMS.addTable(movies);

        //define GENRES table
        ArrayList<String> gNames = new ArrayList<>();
        gNames.add("id"); gNames.add("name");
        ArrayList<String> gTypes = new ArrayList<>();
        gTypes.add("INTEGER"); gTypes.add("VARCHAR(20)");
        ArrayList<Integer> gPKeys = new ArrayList<>();
        gPKeys.add(0);
        Table genresTable = new Table("genres", gNames, gTypes, gPKeys);
        myDBMS.addTable(genresTable);

        //define MOVIEGENRES table
        ArrayList<String> mgNames = new ArrayList<>();
        mgNames.add("movieId"); mgNames.add("genreId");
        ArrayList<String> mgTypes = new ArrayList<>();
        mgTypes.add("INTEGER"); mgTypes.add("INTEGER");
        ArrayList<Integer> mgKeys = new ArrayList<>();
        mgKeys.add(0); mgKeys.add(1);
        Table movieGenres = new Table("movieGenres", mgNames, mgTypes, mgKeys);
        myDBMS.addTable(movieGenres);

        //parse through each movie in the list
        for(Movie m : moviesList){
            ArrayList<String> attributes = new ArrayList<>();
            String mId = Integer.toString(m.getId());
            attributes.add(mId);
            String title = m.getTitle();
            title = title.replace(" ", "_");
            attributes.add(title);
            //rating is on a scale 1-10 with one decimal point, multiply by 10 to get integer between 1-100
            //round to get rid of any floating point precision error
            int rating = (int) Math.round(m.getVote_average() * 10);
            attributes.add(Integer.toString(rating));
            myDBMS.insertCmd("movies", attributes);

            List<Movie.Genre> genreList = m.getGenres();
            //parse through list of genres given
            for(Movie.Genre g : genreList){
                String gId = Integer.toString(g.getId());
                String gName = g.getName();
                //add the genre lookup to the genres table
                ArrayList<String> gAtts = new ArrayList<>();
                gAtts.add(gId); gAtts.add(gName);
                myDBMS.insertCmd("genres", gAtts);
                //add the movie-genre relation to the table
                ArrayList<String> mgAtts = new ArrayList<>();
                mgAtts.add(mId); mgAtts.add(gId);
                myDBMS.insertCmd("movieGenres", mgAtts);
            }
        }
    }

    public static void generateCastTable(List<Credits> creditsList, DBMS myDBMS){

    }

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

    void executeSQL() throws FileNotFoundException {
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
    }
}