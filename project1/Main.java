package project1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import csce315.project1.*;
import project1.antlr4.MyRulesBaseListener;
import project1.antlr4.RulesLexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import project1.antlr4.RulesParser;
import project1.conditional.Cell;


public class Main {
    public static void main(String[] args) throws IOException {
        MovieDatabaseParser parser = new MovieDatabaseParser();

        DBMS myDBMS = new DBMS();

        List<Movie> moviesList = MovieDatabaseParser.deserializeMovies("./data/movies.json");
        generateMovieTable(moviesList, myDBMS);
        System.out.println("movies: \n" + myDBMS.getTable("movies").attributesAsString());
        //myDBMS.showCmd(myDBMS.getTable("movies"));
        List<Credits> creditsList = MovieDatabaseParser.deserializeCredits("./data/credits.json");
        generateCastTable(creditsList, myDBMS);
        generateCrewTable(creditsList,myDBMS);
        System.out.println("casts: \n" + myDBMS.getTable("casts").attributesAsString());
        System.out.println("crew: \n" + myDBMS.getTable("crew").attributesAsString());
        System.out.println("genres: \n" + myDBMS.getTable("genres").attributesAsString());
        System.out.println("movieGenres: \n" + myDBMS.getTable("movieGenres").attributesAsString());
        //myDBMS.showCmd(myDBMS.getTable("casts"));
        //example query:
        System.out.println("Done");
        //System.out.println(listener.getMyDBMS().query(listener, "dogs + cats;"));
        System.out.println(myDBMS.getMostPlayedGenre("Danny_McBride"));
        myDBMS.getActorsByCharacterName("Alex");

        System.out.println(
                myDBMS.getBaconNumber("Will_Patton",  "Jacob_Auzanneau")
        );
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
            title = sanitizeString(title);
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
        //define CASTS table
        ArrayList<String> cNames = new ArrayList<>();
        cNames.add("movieId");
        cNames.add("actorId");
        cNames.add("actorName");
        cNames.add("character");
        ArrayList<String> cTypes = new ArrayList<>();
        cTypes.add("INTEGER");
        cTypes.add("INTEGER");
        cTypes.add("VARCHAR(50)");
        cTypes.add("VARCHAR(50)");
        ArrayList<Integer> cKeys = new ArrayList<>();
        cKeys.add(0); cKeys.add(1);
        Table castTable = new Table("casts", cNames, cTypes, cKeys);
        myDBMS.addTable(castTable);

        for(Credits c : creditsList){
            String movieId = c.getId();
            List<Credits.CastMember> cast = c.getCastMember();
            for(Credits.CastMember cm : cast){
                String actorId = Integer.toString(cm.getId());
                String name = sanitizeString(cm.getName());
                String character = sanitizeString(cm.getCharacter());

                ArrayList<String> castAtts = new ArrayList<>();
                castAtts.add(movieId); castAtts.add(actorId);
                castAtts.add(name); castAtts.add(character);
                myDBMS.insertCmd("casts", castAtts);
            }

        }

    }

    public static void generateCrewTable(List<Credits> creditsList, DBMS myDBMS){
        //define CASTS table
        ArrayList<String> cNames = new ArrayList<>();
        cNames.add("MovieId");
        cNames.add("DirectorId");
        cNames.add("DirectorName");
        ArrayList<String> cTypes = new ArrayList<>();
        cTypes.add("INTEGER");
        cTypes.add("INTEGER");
        cTypes.add("VARCHAR(50)");
        ArrayList<Integer> cKeys = new ArrayList<>();
        cKeys.add(0); cKeys.add(1);
        Table crewTable = new Table("crew", cNames, cTypes, cKeys);
        myDBMS.addTable(crewTable);

        for(Credits c : creditsList){
            String movieId = c.getId();
            List<Credits.CrewMember> crew = c.getCrewMember();
            boolean done = false;
            for(Credits.CrewMember cm : crew){
                if(done)
                    break;
                if(cm.getJob().equals("Director")) {
                    done = true;
                    String DirectorId = Integer.toString(cm.getId());
                    String name = sanitizeString(cm.getName());

                    ArrayList<String> crewAtts = new ArrayList<>();
                    crewAtts.add(movieId);
                    crewAtts.add(DirectorId);
                    crewAtts.add(name);
                    myDBMS.insertCmd("crew", crewAtts);
                }
            }

        }

    }

    private static String sanitizeString(String s){
        String s1 = s.replace(" ", "_");
        String s2 = s1.replaceAll("[^a-zA-Z0-9_]", "");
        return s2;
    }

    public static void unitTesting() throws FileNotFoundException{
        int numOfTests = 3;
        MyRulesBaseListener listener = new MyRulesBaseListener();
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